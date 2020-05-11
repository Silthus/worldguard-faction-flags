[![Build Status](https://github.com/Silthus/worldguard-faction-flags/workflows/Build/badge.svg)](../../actions?query=workflow%3ABuild)
[![codecov](https://codecov.io/gh/Silthus/worldguard-faction-flags/branch/master/graph/badge.svg)](https://codecov.io/gh/Silthus/worldguard-faction-flags)
[![GitHub release (latest SemVer including pre-releases)](https://img.shields.io/github/v/release/Silthus/worldguard-faction-flags?include_prereleases&label=release)](../../releases)
[![Commitizen friendly](https://img.shields.io/badge/commitizen-friendly-brightgreen.svg)](http://commitizen.github.io/cz-cli/)
[![semantic-release](https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg)](https://github.com/semantic-release/semantic-release)

# MassiveCore Factions WorldGuard Flags

> This project is part of a pay what you want open source initiative.
>
> [Find out more on the Spigot forums!](https://www.spigotmc.org/threads/open-small-to-medium-plugin-development-pay-what-you-want-8-years-experience-high-quality.435578/)

This was requested by @jxyrod.

**Short Description**

I’m running a 1.7.10 Bungecord/Spigot server that is using massivecore factions v2.6.0 and worldguard v6.1 and I need a plugin that disables /f claiming and /f autoclaiming on worldguarded regions. Factions can claim up to the region but I don’t want them to be able to claim inside the region. Also if the worldguarded region doesn’t sit equally in a chunk I don't want the player to be able to /f claim and have the claim overlap into the worldguarded region.

**Reference Plugins**

I tried looking into my factions config but worldguard has not been integrated with this version of factions.

**Detailed Feature Description**

Describe each feature you want in detail, including commands, their description, permissions, and so on. Use this checklist to make sure you got everything:

* Short name of the feature: Disable faction claiming in region
* Description of the feature in the form of: When I /region flag [region] claiming deny then it should disable faction claiming in that region
* Needed permission for the feature.- Admin/OP just like regular worldguard
* Commands triggering the feature.- just a feature to disable claiming in regions for worldguard. It doesnt even need to have a command if you just have a plugin that disables claiming from all regions.
* Restrictions or other edge cases, e.g. should not be usable in creative mode. - None at the moment
* API integration with other plugins, e.g. - Massivecore Factions v2.6.0

## Usage

This plugin only adds a custom flag to the existing WorldGuard infrastructure. You can simply apply the new flag as you would any other WorldGuard flag. See the [WorldGuard documentation](https://worldguard.enginehub.org/en/latest/regions/flags/) for details.

```shell script
/region flag your-region faction-claim deny
```

Currently this plugin provides the following custom flags.

| Flag | Type | Description |
| ---- | ---- | ----------- |
| `faction-claim` | `deny/allow` | Denys the claiming of faction chunks inside the given region if set to deny. *Defaults to: **allow*** |

## Supported Minecraft Versions

| Version | Support |
| ------- | :-----: |
| 1.7.10  |   ✔️    |

## Compiling the Project

> **Note**: This project is compiled for a very old version of Minecraft.

You need to [download the Spigot build](https://cdn.getbukkit.org/spigot/spigot-1.7.10-SNAPSHOT-b1657.jar) manually and add it to your local maven repository.

```shell script
mvn install:install-file -Dfile=".\spigot-1.7.10-SNAPSHOT-b1657.jar" -DgroupId="org.spigot" -DartifactId="spigot-api" -Dversion="1.7.10-b1657" -Dpackaging=jar
```

You will also have to [download Factions](https://www.spigotmc.org/resources/factions.1900/) and install it into your local maven repository.

```shell script
mvn install:install-file -Dfile=".\Factions.jar" -DgroupId="com.massivecraft.factions" -DartifactId="Factions" -Dversion="2.14.0" -Dpackaging=jar
mvn install:install-file -Dfile=".\MassiveCore.jar" -DgroupId="com.massivecraft" -DartifactId="core" -Dversion="2.14.0" -Dpackaging=jar
```

Please read the [Contributing Guidelines](CONTRIBUTING.md) before submitting any pull requests or opening issues.

## Deploy Task

You can export your plugin to the plugins directory from your working directory with the Gradle **deploy task**. The task will **build and copy** your plugin **automatically**.

## Debugging the Server

You can use and debug the installed test server by running the Server run configuration. Every time you start the server, the plugin will be deployed. You can disable it, when you edit the Server run configuration.

## Important info

By using this template and starting the server, you agree to the Minecraft EULA automatically, because in this template is the eula file, because then you dont have to agree manually.
