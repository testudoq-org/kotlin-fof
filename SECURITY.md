# Security Policy

## Supported Versions

Only the latest GitHub Release is supported. There is no LTS or backport policy.

| Version | Supported |
|---|---|
| Latest release | Yes |
| Older releases | No |

---

## Reporting a Vulnerability

**Do not open a public GitHub Issue for security vulnerabilities.**

Report privately via GitHub Security Advisories:
**Security tab → Report a vulnerability**

Include:
- Android version and device model
- Steps to reproduce
- Potential impact

You will receive a response within **7 days**. If confirmed, a patched release will be cut as soon as practicable.

---

## Scope

FOFF has a minimal attack surface:

| Area | Notes |
|---|---|
| Network | None — zero internet access or permissions |
| Data collection | None — no analytics, no logging, no storage |
| Permissions | `BIND_ACCESSIBILITY_SERVICE` only |
| Third-party SDKs | None — only AndroidX/Material from Google |

FOFF calls `GLOBAL_ACTION_POWER_DIALOG` exclusively. It does not read screen content, interact with other apps, or perform any action beyond triggering the system power overlay.

---

## Dependency Security

Dependencies are kept up to date via [Dependabot](/.github/dependabot.yml), which raises automated pull requests for Gradle dependency and GitHub Actions version bumps on a weekly schedule.

All current dependencies are Apache 2.0 and maintained by Google or JetBrains:

| Library | Maintained by |
|---|---|
| Kotlin | JetBrains |
| AndroidX Core KTX | Google |
| AppCompat | Google |
| Material Components | Google |
| ConstraintLayout | Google |

---

## APK Integrity

Release APKs attached to GitHub Releases are signed with a private keystore. Verify before installing:

```powershell
$apksigner = "$env:LOCALAPPDATA\Android\Sdk\build-tools\35.0.0\apksigner.bat"
& $apksigner verify --verbose fof-release.apk
```

The SHA-256 certificate fingerprint for legitimate releases is published in the notes of each GitHub Release.
