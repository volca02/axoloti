#!/bin/bash

set -e

echo -e "\n\nAxoloti Install script for Linux"
echo -e "This will install Axoloti"
echo -e "Use at your own risk\n"
echo -e "Some packages will be installed with apt-get,"
echo -e "and all users will be granted permission to access some USB devices"
echo -e "For this you'll require sudo rights and need to enter your password...\n"
# echo -e "Press RETURN to continue\nCTRL-C if you are unsure!\n"
# read

PLATFORM_ROOT="$(cd $(dirname $0); pwd -P)"

ARCH=$(uname -m | sed 's/x86_//;s/i[3-6]86/32/')
if [ -f /etc/lsb-release ]; then
    . /etc/lsb-release
    OS=$DISTRIB_ID
elif [ -f /etc/debian_version ]; then
    OS=Debian  # XXX or Ubuntu??
    if [ -n "`grep 8.6 /etc/debian_version`" ] && [ -z "`uname -m | grep x86_64`" ]; then
      OS=DebianJessie32bit
    fi

elif [ -f /etc/arch-release ]; then
    OS=Archlinux
elif [ -f /etc/gentoo-release ]; then
    OS=Gentoo
elif [ -f /etc/fedora-release ]; then
    OS=Fedora
else
    OS=$(uname -s)
fi

case $OS in
    Ubuntu|Debian|DebianJessie32bit)
        echo "apt-get install -y libtool libudev-dev automake autoconf ant curl lib32z1 lib32ncurses5 lib32bz2-1.0 p7zip-full"
      if [ $OS==DebianJessie32bit ]; then
            sudo apt-get install -y build-essential libtool libudev-dev automake autoconf \
               ant curl p7zip-full fakeroot unzip udev
      else
            sudo apt-get install -y libtool libudev-dev automake autoconf \
               ant curl lib32z1 lib32ncurses5 p7zip-full fakeroot
      fi

        # On more recent versions of Ubuntu
        # the libbz2 package is multi-arch
        install_lib_bz2() {
            sudo apt-get install -y lib32bz2-1.0
        }
        set +e
        if ! install_lib_bz2; then
            set -e
            sudo dpkg --add-architecture i386
            sudo apt-get update
            sudo apt-get install -y libbz2-1.0:i386
        fi
        ;;
    Archlinux|Arch|ManjaroLinux)
        echo "pacman -Syy"
        sudo pacman -Syy
        echo "pacman -S --noconfirm apache-ant libtool automake autoconf curl lib32-ncurses lib32-bzip2 p7zip"
        sudo pacman -S --noconfirm apache-ant libtool automake autoconf curl \
             lib32-ncurses lib32-bzip2 p7zip
        ;;
    Gentoo)
	echo "detected Gentoo"
	;;
    Fedora)
        echo "detected Fedora"
        sudo dnf group install "Development Tools"
        sudo dnf -y install libusb dfu-util libtool libudev-devel automake autoconf \
        ant curl ncurses-libs bzip2
        ;;
    *)
        echo "Cannot handle dist: $OS"
        exit
        ;;
esac

cd "$PLATFORM_ROOT"

./add_udev_rules.sh

mkdir -p "${PLATFORM_ROOT}/bin"
mkdir -p "${PLATFORM_ROOT}/lib"
mkdir -p "${PLATFORM_ROOT}/src"


CH_VERSION=18.2.2
if [ ! -d "${PLATFORM_ROOT}/../ChibiOS_${CH_VERSION}" ];
then
    cd "${PLATFORM_ROOT}/src"
    CH_VERSION=18.2.2
    ARDIR=ChibiOS_${CH_VERSION}
    ARCHIVE=${ARDIR}.zip
    if [ ! -f ${ARCHIVE} ];
    then
        echo "##### downloading ${ARCHIVE} #####"
        curl -L "https://github.com/ChibiOS/ChibiOS/archive/ver${CH_VERSION}.zip" > ${ARCHIVE}
    else
        echo "${ARCHIVE} already downloaded"
    fi
    unzip -q -o ${ARCHIVE}

    mv ChibiOS-ver${CH_VERSION} ${ARDIR}

    cd ${ARDIR}/ext

    pwd
    7z x ./fatfs-0.13_patched.7z
    cd ../../
    rm -rf ../../${ARDIR}
    mv ${ARDIR} ../..


    echo "fixing ChibiOS community from Axoloti/ChibiOS-Contrib"
    cd ${PLATFORM_ROOT}/../${ARDIR}
    rm -rf community
    git clone https://github.com/axoloti/ChibiOS-Contrib.git community
    cd community
    git checkout patch-2

else
    echo "chibios directory already present, skipping..."
fi

if [ ! -f "${PLATFORM_ROOT}/gcc-arm-none-eabi-8-2018-q4-major/bin/arm-none-eabi-gcc" ];
then
    cd "${PLATFORM_ROOT}"
    ARDIR=gcc-arm-none-eabi-8-2018q4
    ARCHIVE_BASE="gcc-arm-none-eabi-8-2018-q4-major"
    ARCHIVE=${ARCHIVE_BASE}-linux.tar.bz2
    if [ ! -f ${ARCHIVE} ];
    then
        echo "downloading ${ARCHIVE}"
        curl -L https://armkeil.blob.core.windows.net/developer/Files/downloads/gnu-rm/8-2018q4/${ARCHIVE} > ${ARCHIVE}
    else
        echo "${ARCHIVE} already downloaded"
    fi
    tar xfj ${ARCHIVE}
    rm ${ARCHIVE}
else
    echo "gcc-arm-none-eabi-8-2018-q4-major/bin/arm-none-eabi-gcc already present, skipping..."
fi

if [ ! -f "$PLATFORM_ROOT/lib/libusb-1.0.a" ];
then
    cd "${PLATFORM_ROOT}/src"
    ARDIR=libusb-1.0.19
    ARCHIVE=${ARDIR}.tar.bz2
    if [ ! -f ${ARCHIVE} ];
    then
        echo "##### downloading ${ARCHIVE} #####"
        curl -L http://sourceforge.net/projects/libusb/files/libusb-1.0/$ARDIR/$ARCHIVE/download > $ARCHIVE
    else
        echo "##### ${ARCHIVE} already downloaded #####"
    fi
    tar xfj ${ARCHIVE}

    cd "${PLATFORM_ROOT}/src/libusb-1.0.19"

    patch -N -p1 < ../libusb.stdfu.patch

    ./configure --prefix="${PLATFORM_ROOT}"
    make
    make install

else
    echo "##### libusb already present, skipping... #####"
fi

if [ ! -f "${PLATFORM_ROOT}/bin/dfu-util" ];
then
    cd "${PLATFORM_ROOT}/src"
    ARDIR=dfu-util-0.8
    ARCHIVE=${ARDIR}.tar.gz
    if [ ! -f $ARCHIVE ];
    then
        echo "##### downloading ${ARCHIVE} #####"
        curl -L http://dfu-util.sourceforge.net/releases/$ARCHIVE > $ARCHIVE
    else
        echo "##### ${ARCHIVE} already downloaded #####"
    fi
    tar xfz ${ARCHIVE}

    cd "${PLATFORM_ROOT}/src/${ARDIR}"
    ./configure --prefix="${PLATFORM_ROOT}" USB_LIBS="${PLATFORM_ROOT}/lib/libusb-1.0.a -ludev -pthread" USB_CFLAGS="-I${PLATFORM_ROOT}/include/libusb-1.0/"
    make
    make install
    make clean
    ldd "${PLATFORM_ROOT}/bin/dfu-util"
else
    echo "##### dfu-util already present, skipping... #####"
fi

case $OS in
    Ubuntu|Debian)
        echo "apt-get install openjdk-8-jdk"
        sudo apt-get install openjdk-8-jdk
        ;;
    Archlinux)
        echo "pacman -Syy jdk8-openjdk"
        sudo pacman -S --noconfirm jdk8-openjdk
        ;;
    Gentoo)
	echo "emerge --update jdk:1.8 ant"
	sudo emerge --update jdk:1.8 ant
	;;
esac


echo "##### compiling firmware... #####"
cd "${PLATFORM_ROOT}"
./compile_firmware.sh

echo "##### building GUI... #####"
cd "${PLATFORM_ROOT}"/..
ant

echo "DONE"
