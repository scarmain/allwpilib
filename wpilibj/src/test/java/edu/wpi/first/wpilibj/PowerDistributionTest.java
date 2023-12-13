// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package edu.wpi.first.wpilibj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.simulation.PDPSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.junit.jupiter.api.Test;

class PowerDistributionTest {
  @Test
  void testGetAllCurrents() {
    HAL.initialize(500, 0);
    PowerDistribution pdp = new PowerDistribution(1, ModuleType.kRev);
    PDPSim sim = new PDPSim(pdp);

    for (int i = 0; i < pdp.getNumChannels(); i++) {
      sim.setCurrent(i, 24 - i);
    }

    SmartDashboard.putData(pdp);
    LiveWindow.setEnabled(true);
    LiveWindow.updateValues();

    for (int i = 0; i < pdp.getNumChannels(); i++) {
      var value = SmartDashboard.getNumber("PowerDistribution[1]/Chan" + i,-1);
      assertEquals(24 - i, value);
    }
  }
}
