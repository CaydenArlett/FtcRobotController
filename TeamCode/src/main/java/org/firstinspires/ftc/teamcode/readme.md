## TeamCode Module
Completed:
- Foundation for autonomous linear arm & launcher
- Basic movement is completed

To-do:
- test arm code
  - test all foundational stuff once mech is ready with the robot
  - Calculate/set unit variables (ie, motor turns to extend arm x cm)
    - Linear arm
- Make claw code (Finished Conceptually)
- Code pinpoint (position of robot on the field)
  - Specifically, measure the x and y distances as shown in Drive.java
  - Then implement pinpoint into autos
- More detailed autonomous (laucher & armcode)
- Add clawLeft and clawRight to configuration
- I think I named the slide motor "launcher" in the config so change that to slideMotor
- program servos

Buttonmaps: 
- gamepad1: 
  - leftbumper: counterclockwise rotation
  - rightbumper: clockwise rotation
  - left joystick: Strafe
  - Right joystick: turning again
  - a: resets the linear slide to the bottom
- gamepad2: 
  - dpad: linear slide movement
  - Bumpers: corresponding side of claw
  - x: latch