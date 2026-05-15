# Contributing to FOFF

Thanks for taking an interest. FOFF is a small utility project — contributions are welcome, but kept intentionally lean. Read this before opening a PR.

---

## Philosophy

FOFF exists to do one thing well: open the Android power menu in a single tap. Contributions that serve that goal are welcome. Contributions that add scope, complexity, or features the app doesn't need will be declined, respectfully.

> If in doubt: fewer lines of code is usually better.

---

## What We Welcome

- **Bug fixes** — especially around accessibility service reliability or OEM quirks
- **Accessibility improvements** — better contrast, content descriptions, screen reader support
- **Gradle / dependency updates** — keeping the build healthy
- **Documentation** — corrections, clarifications, translations
- **UI polish** — within the existing monochrome aesthetic, no feature additions

---

## What We Don't Want

- New features that go beyond opening the power menu
- Third-party analytics, crash reporting, or tracking libraries
- Network permissions or internet access of any kind
- Changes to the monochrome design system (colour, fonts, layout restructures)
- Minification or obfuscation changes that complicate the accessibility service trust model

---

## Getting Started

1. Fork the repository
2. Create a branch from `master`: `git checkout -b fix/your-description`
3. Make your changes in `FOF/`
4. Build and verify:
   ```powershell
   $env:JAVA_HOME = "C:\Program Files\OpenJDK\jdk-21.0.1"
   $env:ANDROID_HOME = "$env:LOCALAPPDATA\Android\Sdk"
   cd FOF
   .\gradlew.bat assembleDebug
   ```
5. Test on a real device — the emulator doesn't have a physical power menu
6. Open a pull request with a clear description of what you changed and why

---

## Code Style

- Kotlin only — no Java
- No third-party libraries unless absolutely unavoidable and clearly justified
- Follow existing patterns — the static `companion object` instance in `FofAccessibilityService` is intentional; don't replace it with service binding
- Keep files short — `MainActivity.kt` and `FofAccessibilityService.kt` should stay minimal
- No comments explaining what the code does unless the _why_ is genuinely non-obvious

---

## Reporting Issues

Open a GitHub Issue with:

1. Android version and device model
2. What you expected to happen
3. What actually happened
4. Whether re-enabling the accessibility service fixes it

---

## License

By contributing, you agree that your contributions will be licensed under the same **CC BY-NC 4.0** license as the rest of the project. See [LEGAL.md](LEGAL.md).
