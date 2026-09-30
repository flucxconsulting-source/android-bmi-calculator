# BMI Calculator

A beginner-friendly Android BMI calculator built with Kotlin, XML layouts, and Material Components.

## What the app does

- Select height with a Material slider.
- Adjust weight with plus and minus buttons.
- Calculate BMI using the standard formula: `weight / height²`.
- Show the BMI value, category, color, and guidance text.
- Reset the calculator to default values.
- Explain BMI limitations in a guidance dialog.
- Support English and Spanish string resources.

## Concepts practiced

- Android project structure
- Kotlin activity code
- XML layouts
- View IDs and `findViewById`
- String resources and localization
- Color resources
- Button click listeners
- Material dialogs
- Git and GitHub workflow

## BMI categories used

| BMI range | Category |
| --- | --- |
| Less than 18.5 | Underweight |
| 18.5 to 24.9 | Normal |
| 25.0 to 29.9 | Overweight |
| 30.0 to 34.9 | Obesity |
| 35.0 and above | Extreme obesity |

BMI is a screening estimate, not a medical diagnosis.

## How to run

1. Open the project in Android Studio.
2. Let Gradle sync finish.
3. Choose an emulator or connected Android device.
4. Press Run.

## GitHub workflow

After making changes locally:

```powershell
git status
git add .
git commit -m "Finish BMI calculator app"
git push
```

Use `git status` before and after each commit so you know exactly what changed.
