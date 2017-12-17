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
package qcmds;

import axoloti.patch.PatchController;
import axoloti.patch.PatchModel;
import axoloti.patch.PatchViewCodegen;
import axoloti.patch.object.parameter.ParameterInstance;

/**
 *
 * @author Johannes Taelman
 */
public class QCmdGuiDialTx implements QCmdGUITask {

    @Override
    public void DoGUI(QCmdProcessor processor) {
        if (processor.isQueueEmpty()) {
            PatchViewCodegen patchController = processor.getPatchController();
            if (patchController != null) {
                for (ParameterInstance p : patchController.getParameterInstances()) {
                    if (p.getNeedsTransmit()) {
                        if (processor.hasQueueSpaceLeft()) {
                            processor.AppendToQueue(new QCmdSerialDialTX(p.TXData()));
                            //processor.println("tx dial " + p.getName());
                        } else {
                            break;
                        }
                    }
                }
                /* FIXME: live preset updating
                if (patchController.isPresetUpdatePending() && processor.hasQueueSpaceLeft()) {
                    byte pb[] = new byte[patchModel.getSettings().GetNPresets() * patchModel.getSettings().GetNPresetEntries() * 8];
                    int p = 0;
                    for (int i = 0; i < patchModel.getSettings().GetNPresets(); i++) {
                        int pi[] = patchModel.DistillPreset(i + 1);
                        for (int j = 0; j < patchModel.getSettings().GetNPresetEntries() * 2; j++) {
                            pb[p++] = (byte) (pi[j]);
                            pb[p++] = (byte) (pi[j] >> 8);
                            pb[p++] = (byte) (pi[j] >> 16);
                            pb[p++] = (byte) (pi[j] >> 24);
                        }
                    }
                    processor.AppendToQueue(new QCmdUpdatePreset(pb));
                    patchController.setPresetUpdatePending(false);
                }
                */
            }
        }
    }

    @Override
    public String GetStartMessage() {
        return null;
    }

    @Override
    public String GetDoneMessage() {
        return null;
    }
}
