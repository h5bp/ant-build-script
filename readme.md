The Ant Build Script is a tool that optimizes your code for production use on the web. 

It's designed to work with HTML5 Boilerplate with minimal configuration, but it's also here to serve as a rich source of Ant tasks you can use as the basis for your own custom build scripts.

##Compatibility
While it should work with any version of HTML5 Boilerplate (with varying degrees of intervention) v1.0 of the Build Script was developed and tested against v4.0 of HTML5 Boilerplate. 

## Why use it?

Faster page load times, improved workflow and happy end users :)

## What it does

* Combines and minifies javascript (via Closure Compiler)
* Inlines stylesheets specified using `@import` in your CSS
* Combines and minifies CSS (using YUI Compressor)
* Optimizes JPGs and PNGs (with jpegtran, advpng & optipng)
* Basic to aggressive html minification (via htmlcompressor)
* Revises the file names of your assets so that you can use heavy caching (1 year expires).
* Updates your HTML to reference these new hyper-optimized CSS + JS files
* Updates your HTML to use the minified jQuery instead of the development version
* Remove unneeded references from HTML (like a root folder favicon)
* Runs your JavaScript through a code quality tool like JSLint or JSHint (optional)
* Runs your CSS through a code quality tool, CSSLint (optional)
* Cache-busting support for a module directory
* Optionally precompile LESS formatted CSS
* Optionally precompile SASS formatted CSS
* Optionally output JSDOC3 documentation
* Optionally convert your JPGs to progressive JPGs
* Optionally validates your HTML
* Optionally set your script to use `async` or `defer`

## Add the build script to your project

For most people, you can just drop the contents of the build script repo into your HTML5 Boilerplate project in a `build` folder. That's the easiest way to get up and running.

###Add the build script to your project (Advanced version)
Since we split out the build scripts from the main h5bp repo, you now have more options on how to integrate a build script into your project.As was mentioned you can simply drop the contents of the build script repo into a `build` folder and you're good to go. 

If you'd like to merge it into your main HTML5 Boilerplate repository and preserve the build script commit history (and the ability to update from Github), alongside the H5BP commit history, please follow this workflow:

```
# Move into your project's git repository
cd my-project
# Create and checkout a new feature branch
git checkout -b ant-build-script
# Create a new remote called "h5bp-ant-bs".
# Fetch the build script from the remote repository.
git remote add -f h5bp-ant-bs git://github.com/h5bp/ant-build-script.git
git merge -s ours --no-commit h5bp-ant-bs/master
# Put the build script into a subdirectory `build/`
git read-tree --prefix=build/ -u h5bp-ant-bs/master
# Commit the merge (preserve the build script history too)
git commit -m "Subtree merge H5BP ant build script"
# Update the build script subtree if needed
git pull -s subtree h5bp-ant-bs master
# Merge back into master branch if everything went according to plan
```

## Requirements

Out of the box, the build script requires Java 1.6.

Ant itself requires the Java JDK, version 1.4 or later. 1.5 or later is strongly recommended.

Closure Compiler, our tool for script minification, requires Java 1.6.

This means that OS X versions prior to 10.6 are no longer supported out of the box.
[SoyLatte][soylatte] provides 10.4 and 10.5 builds of OpenJDK 7 for Intel OS X machines. However, only OS X 10.5 builds of OpenJDK 7 are available for PowerPC based Macs due to a bug in the 10.4 Compiler.
( Be sure to read the Download link as the archives are password protected "to provide a click though agreement" of the JDK licensing. )

[soylatte]: http://landonf.bikemonkey.org/static/soylatte/

Alternatively, YUI Compressor, which requires Java > 1.4, could be swapped out for Closure Compiler.

## Quick Start
**You must wrap any scripts to concatenate in a pair of specially constructed comments. These look like this:**

*legacy version (pre 1.0)* (https://github.com/h5bp/ant-build-script/wiki/What-version-are-you-running%3F)
```html
    <!-- scripts concatenated and minified via build script -->
    <script src="js/plugins.js"></script>
    <script src="js/main.js"></script>
    <!-- end scripts -->
```

*1.0 and greater*
```html
  <!-- //-beg- concat_js -->
  <script src="js/plugins.js"></script>
  <script src="js/main.js"></script>
  <!-- //-end- concat_js -->
```
## If you're on Mac or Linux...

You've got all your dependencies pre-installed, likely. You need to install ant-contrib to get the build script working.

On Linux use `yum install ant-contrib`.

On Mac, install [MacPorts](http://www.macports.org/install.php) and then do `sudo port install ant-contrib`. 

You should test what version of optipng you have. IF it's less than 0.7.0 then you need to uncomment [line 189 of project.properties](config/project.properties#L189). 

## If you're on Windows...

* Get the [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (JRE isn't enough).
* Get [WinAnt](http://code.google.com/p/winant/) and point the installer to `Program Files/Java/jre6/bin/`
* For detailed information on installing Ant for Windows, see this [step-by-step instruction by Nicholas Zakas](http://www.nczonline.net/blog/2012/04/12/how-to-install-apache-ant-on-windows/) or our own [detailed instructions](https://github.com/h5bp/ant-build-script/wiki/Detailed-Ant-Installation-Instructions)

## Using the Build Script

* 1) Mac users, open the application "Terminal".  Windows users, open command line interface by doing Start Menu > Run > `cmd.exe`.  However, Windows users, we created a friendly `runbuildscript.bat` file for you if you'd like to avoid the command line and navigate to the build directory within your project. 

For those of you new to terminal or command line, use the change directory command followed by the directory path. for example...

    cd sites/your-site/build/

> Note: To ensure you've navigated to the correct directory, you may want to now check the files within the current directory. Mac users can type "ls" in terminal. Windows users should type "dir" in command line. If the file list returned is what you were expecting, move to step 2. Otherwise, check the directory location in Finder or Windows Explorer and start over.

* 2) Next, simply type:

    ant build

The H5BP build script will begin to run and compress your files.  At the very end you should see "BUILD SUCCESSFUL" followed by the total time it took to build.

* 3) Now, look in your H5BP project folder and see that there is a newly created "publish" directory within your project.  Inside, you will find your minified CSS, JS and along with duplicates of the files from your original directory. This new set of files within "publish" is your production code.  The site should look and function the same in browser as it did before, but only now faster!

> Note: If your new pages do not render in browser the same as they did before your ran the build script, visit the Troubleshooting section below.

## Going Further

There are a few different build options:

    ant build     # minor html optimizations (extra quotes removed). inline script/style minified (default)
    ant buildkit  # all html whitespace retained. inline script/style minified 
    ant basics    # same as build minus the basic html minfication
    ant minify    # same as build plus full html minification
    ant text      # same as build but without image (png/jpg) optimizing

Your build will be added to the `publish/` folder. **BOOM!** you're done.
