# Flux

![Workflow result](https://github.com/fidloo/flux/workflows/Check/badge.svg)


## :scroll: Description
Flux is a dynamic weather that communicates the weather throughout a landscape whose details vary according to the time and the weather at that particular time.
This dynamic landscape follows a day / night cycle with multiple layers that vary regarding the phase of the day (night, sunrise, day and sunset). The sun and the moon are drawn using a quadratic function computed according to the available space for simplicity purposes.
In addition to the day / night cycle, a particle generation system has been created to draw generic types of particles : 
- Lines
- Points
- Images

As it is fully customizable, the particle generator was used to draw snow, rain (light, heavy and thunderstorm) as well as clouds on the landscape view using Jetpack Compose Canvas.
This canvas also draws lighting with a random path when the displayed weather is a thunderstorm.

Flux also displays basic weather info via four sections:
- Details: current weather
- Hourly weather : A chart showing the temperature, wind or cloud cover according to the time. You can click on the temperature / wind / cloud cover filter and the curve will animates to the target state. The curve is drawn on a Canvas and interpolated using Bezier method and connections points. You can also click on the time at the bottom of the chart to update the time shown in the dynamic landscape.
- Weather radar
- This week : 7-day forecast

Flux has a light and dark theme that follows your device preference.
A lot of animations have been used in this project (landscape, curve, expand / collapse, fade, etc.)

This project follows an architecture by layer (data, domain, presentation).



## :bulb: Motivation and Context
It's very impressive how fast an app can be developed using Jetpack Compose and its underlying tools (easy theming, state management, navigation, animation). The Android Dev Challenge was a great way to discover the power of Jetpack Compose.

## :camera_flash: Screenshots

### Dark Theme
<img src="/results/screenshot_1.png" width="260">&emsp;<img src="/results/screenshot_2.png" width="260">&emsp;<img src="/results/screenshot_3.png" width="260">&emsp;<img src="/results/screenshot_4.png" width="260">

### Light Theme
<img src="/results/screenshot_1_light.png" width="260">&emsp;<img src="/results/screenshot_2_light.png" width="260">&emsp;<img src="/results/screenshot_3_light.png" width="260">&emsp;<img src="/results/screenshot_4_light.png" width="260">


## License
```
Copyright 2020 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```