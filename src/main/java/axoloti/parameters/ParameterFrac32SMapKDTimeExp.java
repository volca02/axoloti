/**
 * Copyright (C) 2013, 2014 Johannes Taelman
 *
 * This file is part of Axoloti.
 *
 * Axoloti is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * Axoloti is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Axoloti. If not, see <http://www.gnu.org/licenses/>.
 */

/* Oops, this class is hard-referenced in .axo files... */
package axoloti.parameters;

import axoloti.object.parameter.ParameterFrac32SMap;
import axoloti.patch.object.parameter.ParameterInstanceFrac32SMap;
import axoloti.realunits.LinearTimeExp;
import axoloti.realunits.NativeToReal;

/**
 *
 * @author Johannes Taelman
 */
public class ParameterFrac32SMapKDTimeExp extends ParameterFrac32SMap {

    public ParameterFrac32SMapKDTimeExp() {
        super();
    }

    public ParameterFrac32SMapKDTimeExp(String name) {
        super(name);
    }

    @Override
    public ParameterInstanceFrac32SMap InstanceFactory() {
        ParameterInstanceFrac32SMap p = super.InstanceFactory();
        p.SetPFunction("parameter_function::pf_kexpdtime");
        NativeToReal convs[] = {new LinearTimeExp()};
        p.convs = convs;
        return p;
    }

    static public final String TypeName = "frac32.s.map.kdecaytime.exp";

    @Override
    public String getTypeName() {
        return TypeName;
    }
}
