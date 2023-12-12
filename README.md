[![Maven](https://img.shields.io/maven-central/v/io.github.cleydyr/dart-sass-maven-plugin.svg)](https://repo1.maven.org/maven2/io/github/cleydyr/dart-sass-maven-plugin/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java CI](https://github.com/cleydyr/dart-sass-maven-plugin/workflows/Java%20CI/badge.svg)](https://github.com/cleydyr/dart-sass-maven-plugin/actions/)

# Dart Sass Maven Plugin

A Maven plugin that allows to compile SASS using Dart Sass

<img height="48" src="https://rawgit.com/sass/sass-site/master/source/assets/img/logos/logo.svg">

## Usage
```xml
<build>
...
		<plugins>
			<plugin>
				<groupId>io.github.cleydyr</groupId>
				<artifactId>dart-sass-maven-plugin</artifactId>
				<version>1.0.0</version>
				<executions>
					<execution>
						<id>generate-css-using-sass</id>
						<phase>generate-resources</phase>
						<goals>
							<goal>compile-sass</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
...
</plugins>
</build>
```

## Supported systems
Dart Sass Maven Plugin will run on any combination of operating systems and architectures that are available on the [Dart Sass releases page](https://github.com/sass/dart-sass/releases. As of now they are:

|  |Linux|Mac|Windows|
|--|-----|---|-------|
|x86|✅|❌|✅|
|x64|✅|✅|✅|
|arm64|✅|✅|❌|
|arm|✅|❌|❌|

## Offline operation
In previous versions, this plugin included all released archives for a given Dart Sass version. However, starting from version 1.0.0, this plugin will download the latest release of Dart Sass, if it's not found on the temporary folder or on the cached files directory. Should this plugin be used in an air-capped environment, you should provide an release archive inside the cached files directory and at least pin the Dart Sass' release version with the `<version>`. The release archive should be a file named `release` on the subpath `<os>`/`<arch>`/`<version>`/ inside the cached files directory.

## Goals

### compile-sass
Goal that compiles a set of sass/scss files from an input directory to an output directory.

Full name: `io.github.cleydyr:dart-sass-maven-plugin:0.4.0:compile-sass`

Binds by default to the lifecycle phase: `process-resources`.

#### Parameter details
`<arch>`

This parameter represents the Dart Sass architecture that should be used to compile Sass files. If letf unset, it will be autodetected by the plugin. Accepted values are "x64", "aarch32", "aarch64" and "ia32".<br>
**Type**: String<br>
**Required**: No<br>

`<os>`

This parameter represents the Dart Sass operating system that should be used to compile Sass files. If letf unset, it will be autodetected by the plugin. Accepted values are "linux", "macos" and "windows".<br>
**Type**: String<br>
**Required**: No

`<version>`

This parameter represents the Dart Sass version that should be used to compile Sass files. If left unset, the version available at https://github.com/sass/dart-sass/releases/latest will be used.<br>
**Type**: String<br>
**Required**: No<br>

`<cachedFilesDirectory>`

This parameter represents a path in the local file system where the release archive downloaded from the internet will be stored. If letf unset, it will default to
<ul>
<li>$HOME/.cache/dart-sass-maven-plugin/ on *nix operating systems; or</li>
<li>%LOCALAPPDATA%\dart-sass-maven-plugin\Cache on Windows operating systems.</li>
</ul>

**Type**: java.io.File<br>
**Required**: No

`<color>`

This flag tells Sass to emit terminal colors. By default, it will emit colors if it looks like it's being run on a terminal that supports them. Set it to false to tell Sass to not emit colors.<br>
**Type**: boolean<br>
**Required**: No<br>
**Default**: true

`<embedSourceMap>`

This flag tells Sass to embed the contents of the source map file in the generated CSS, rather than creating a separate file and linking to it from the CSS.<br>
**Type**: boolean<br>
**Required**: No<br>
**Default**: false

`<embedSources>`

This flag tells Sass to embed the entire contents of the Sass files that contributed to the generated CSS in the source map. This may produce very large source maps, but it guarantees that the source will be available on any computer no matter how the CSS is served.<br>
**Type**: boolean<br>
**Required**: No<br>
**Default**: false

`<errorCSS>`

This flag tells Sass whether to emit a CSS file when an error occurs during compilation. This CSS file describes the error in a comment and in the content property of body::before, so that you can see the error message in the browser without needing to switch back to the terminal.
By default, error CSS is enabled if you're compiling to at least one file on disk (as opposed to standard output). You can activate errorCSS explicitly to enable it even when you're compiling to standard out (not supported by this Maven plugin), or set it explicitly to false to disable it everywhere. When it's disabled, the update flag and watch flag (the latter being not yet supported by this Maven plugin) will delete CSS files instead when an error occurs.<br>
**Type**: boolean<br>
**Required**: No<br>
**Default**: true

`<inputFolder>`

Path to the folder where the sass/scss are located.<br>
**Type**: java.io.File<br>
**Required**: No<br>
**Default**: src/main/sass

`<loadPaths>`

Paths from where additional load path for Sass to look for stylesheets will be loaded. It can be passed multiple times to provide multiple load paths. Earlier load paths will take precedence over later ones.<br>
**Type**: java.util.List<br>
**Required**: No

`<noCharset>`

This flag tells Sass never to emit a @charset declaration or a UTF-8 byte-order mark. By default, or if the charset flag is activated, Sass will insert either a @charset declaration (in expanded output mode) or a byte-order mark (in compressed output mode) if the stylesheet contains any non-ASCII characters.<br>
**Type**: boolean<br>
**Required**: No<br>
**Default**: false

`<noSourceMap>`

If the noSourceMap flag is set to true, Sass won't generate any source maps. It will void other source map options (namely sourceMapURLs, embedSources and embedSourceMap) if explicitly set.<br>
**Type**: boolean<br>
**Required**: No<br>
**Default**: false

`<noUnicode>`

This flag tells Sass only to emit ASCII characters to the terminal as part of error messages. By default, Sass will emit non-ASCII characters for these messages. This flag does not affect the CSS output.<br>
**Type**: boolean<br>
**Required**: No<br>
**Default**: false

`<outputFolder>`

Path to the folder where the css and source map files will be created.<br>
**Type**: java.io.File<br>
**Required**: No<br>
User Property: project.build.directory

`<quiet>`

This flag tells Sass not to emit any warnings when compiling. By default, Sass emits warnings when deprecated features are used or when the @warn rule is encountered. It also silences the @debug rule. Enabling it will also silence output when in watch mode.<br>
**Type**: boolean<br>
**Required**: No<br>
**Default**: false

`<quietDeps>`

This flag tells Sass not to emit deprecation warnings that come from dependencies. It considers any file that's transitively imported through a load path to be a "dependency". This flag doesn't affect the @warn rule or the @debug rule.<br>
**Type**: boolean<br>
**Required**: No<br>
**Default**: false

`<sourceMapURLs>`

This option controls how the source maps that Sass generates link back to the Sass files that contributed to the generated CSS. Dart Sass supports two types of URLs:<br>
relative (the default) uses relative URLs from the location of the source map file to the locations of the Sass source file;
and
absolute uses the absolute file: URLs of the Sass source files. Note that absolute URLs will only work on the same computer that the CSS was compiled.
Use either `RELATIVE` or `ABSOLUTE` in the plugin configuration.<br>
**Type**: com.github.cleydyr.dart.command.enums.<br>SourceMapURLs
**Required**: No<br>
**Default**: RELATIVE

`<stopOnError>`

This flag tells Sass to stop compiling immediately when an error is detected, rather than trying to compile other Sass files that may not contain errors. It's mostly useful in many-to-many mode (which is the mode currently supported by this Maven plugin).<br>
**Type**: boolean<br>
**Required**: No<br>
**Default**: false

`<style>`

This option controls the output style of the resulting CSS. Dart Sass supports two output styles:
expanded (the default), which writes each selector and declaration on its own line; and
compressed, which removes as many extra characters as possible, and writes the entire stylesheet on a single line.
Use either EXPANDED or COMPRESSED in the plugin configuration.<br>
**Type**: com.github.cleydyr.dart.command.enums.Style<br>
**Required**: No<br>
**Default**: EXPANDED

`<trace>`

This flag tells Sass to print the full Dart stack trace when an error is encountered. It's used by the Sass team for debugging errors.<br>
**Type**: boolean<br>
**Required**: No<br>
**Default**: false

`<update>`

If the this flag is set to true, Sass will only compile stylesheets whose dependencies have been modified more recently than the corresponding CSS file was generated. It will also print status messages when updating stylesheets.<br>
**Type**: boolean<br>
**Required**: No<br>
**Default**: false

### watch-sass
Goal that compiles a set of sass/scss files from an input directory to an output directory keeps the process opened watching for changes in the source files. Each time a source file is changed, a message will be written to the standard output.

Full name: `io.github.cleydyr:dart-sass-maven-plugin:0.4.0:watch-sass`

By default it binds to no lifecycle phase.

#### Parameter details

In addition to the parameters that are available to the `compile-sass` goal, the following parameter is also available:

`<poll>`

This flag tells Sass to manually check for changes to the source files every so often instead of relying on the operating system to notify it when something changes. This may be necessary if you’re editing Sass on a remote drive where the operating system’s notification system doesn’t work.<br>
**Type**: boolean<br>
**Required**: No<br>
**Default**: false
