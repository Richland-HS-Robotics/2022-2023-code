# RHS Gold team code
This repo is the RHS Robotics gold team code.

## How to sync with Onbot Java
Use [ftc_http](https://github.com/TheLostLambda/ftc_http).

| Flag        | To do                                     |
|-------------|-------------------------------------------|
| ftc_http -b | Build code on robot                       |
| ftc_http -d | Download code from robot to your computer |
| ftc_http -u | Upload code to robot from your computer   |
| ftc_http -w | Wipe all code from robot controller.      |



## TODO
- [ ] Setup the claw and lifting system
- [ ] Work on an autonomous mode
  - Potentially some sensors/cameras and image processing? (See [here](http://ftc-docs.firstinspires.org/programming_resources/vision/tensorflow_pp_2022/tensorflow_pp_2022.html))

## Documentation

<https://ftctechnh.github.io/ftc_app/doc/javadoc/index.html>

## Robot controls


- Left stick up/down controls forward/back.
- Left stick left/right controls strafe left/right.
- Right stick left/right controls turning left/right.
- Right stick up/down controls raising/lowering arm.
- Button x open grabber
- Button a puts grabber at medium
- Button b close grabber



| Control                                              | What it does                                           |
|------------------------------------------------------|--------------------------------------------------------|
| Left stick vertical without holding right trigger    | Forward/Backwards                                      |
| Left stick horizontal without holding right trigger  | Strafe left/right                                      |
| Right stick horizontal without holding right trigger | Turn left or right                                     |
| Right stick vertical while holding right trigger     | Raise and lower arm                                    |
| Button a                                             | Open grabber to medium (gripping the cone from inside) |
| Button b                                             | Close grabber (not gripping cone)                      |
| Button x                                             | Open grabber to maximum (not useful)                   |
