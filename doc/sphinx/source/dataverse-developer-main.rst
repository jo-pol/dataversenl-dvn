====================================
Developers Guide
====================================

DVN Developers Guide, v. 3.0
++++++++++++++++++++++++++++++++++

This is the updated guide for setting up a developers environment for
DVN version 3.x.

Please note that this document has been completely rewritten as of DVN
3.x. There were enough serious changes in the version 3 of the DVN -
Glassfish v3, Java EE6, NetBeans 7+, IceFaces 2.0 and more - to warrant
creating a new document from scratch, rather than attempting to modify
the old one. An effort was also made to make the process simpler, with a
scripted installer provided specifically for developers, etc. 

As of DVN 3.4 (released April 2013), this guide needs another rewrite
because the source code has moved from SourceForge to GitHub. A new
guide is being prepared at
`http://devguide.thedata.org/ <http://devguide.thedata.org/>`__ and will
be merged into this one.

Build Environment (NetBeans)
==========================

This chapter describes setting up the build environment that you will
need to build the DVN application from source code. 

Quick Checklist
---------------------

(the individual tasks are explained in detail in the sections below).

#. Install Netbeans and Glassfish
#. Install IceFaces 2.0, libraries and NetBeans plugins
#. Check out new copy of the DVN source
#. Select Glassfish server
#. Configure NetBeans libraries
#. Build the application

Install Netbeans and Glassfish
---------------------------------------------

As of DVN version 3.1, the development environment requires NetBeans
7.0.1 and Glassfish 3.1.2.  Unfortunately, NetBeans project does not
offer these 2 versions bundled together. So you have to first install
the NetBeans 7.0.1/Glassfish 3.1.1. bundle; and then upgrade
Glassfish to 3.1.2.

We strongly recommend that you run both installs **as a regular user.** There's no reason to run your development environment as root.

| **Install NetBeans bundle:**

Download NetBeans 7.0.1 Java EE + Glassfish Open Source Edition 3.1.1
bundle from
`http://netbeans.org/downloads/7.0.1/ <http://netbeans.org/downloads/7.0.1/>`__.

For MacOS, you will download a .dmg disk image that will open
automatically and start the installer for you. Choose the typical
installation.

Note that you don’t have to uninstall your existing NetBeans version.
You can have as many versions installed as you need in parallel. So it
is very easy to preserve your current NB6 development environment.

When you start your new NetBeans 7 for the first time, you will be
asked if you want to import the settings from the previous
installations. If you have an existing, pre-DVN 3.\* development
environment on your system,  **answer “no” -- we want to create the new
configuration from scratch.**

| **Install Glassfish 3.1.2**

We **strongly** recommend that you install GlassFish Server 3.1.2,
Open Source Edition, **Full Platform**.It can be obtained from:

`http://glassfish.java.net/downloads/3.1.2-final.html <http://glassfish.java.net/downloads/3.1.2-final.html>`__

The page above contains a link to the installation instructions; but the
process is very straightforward - just download and run the installer.

Make sure you have Sun/Oracle Java JDK version 1.6, and the newest (or
at least, recent) build number available for your platform. (As of
writing this, the latest build number available is 31. On MacOS X, since
the JDK is part of OS distribution, the version currently provided by
Apple should be sufficient)

Note that you don't have to uninstall Glassfish 3.1.1, or any other
versions you may still have around. It's ok to have multiple versions
installed. But make sure you have the 3.1.2 installation selected as the
active server in NetBeans (see section 4 of this guide).

**Important:** During the installation, leave the admin password fields
blank. This is not a security risk, since out of the box, Glassfish
3.1.2 will only be accepting admin connections on the localhost
interface. Choosing password at this stage however will complicate the
installation process unnecessarily. Since this is a development
system, you can probably keep this configuration unchanged (admin on
localhost only). If you need to be able to connect to the admin console
remotely, please see the note in the Appendix section of the main
Installers Guide.

Install IceFaces 2.0 plugin for NetBeans
----------------------------------------------------

Download the NetBeans Integration Plugin version 2.0.2 from:

| `http://icefaces.com/main/downloads/os-downloads.iface <http://icefaces.com/main/downloads/os-downloads.iface>`__.

[Note: You will have to register with the IceFaces project before you
can download the goods. It only takes a minute; we also have the
integration bundle cached here, for your downloading pleasure and
convenience:

`http://dvn.iq.harvard.edu/dev/icefaces/ICEfaces-2.0.2-Netbeans-7.0.zip] <http://dvn.iq.harvard.edu/dev/icefaces/ICEfaces-2.0.2-Netbeans-7.0.zip>`__

Install NetBeans integration plugins. Note that the installation
instructions that come with the plugin bundle are a bit off: some file
names and locations do not match what’s really in the archive. It’s ok.
The installation is still very straightforward: unzip the archive; in
Netbeans, go under tools -> plugins, select “downloaded” and navigate to
the directory where you unpacked the zip file. Select the 3 `.nbm` files
found there, click “install” and follow the prompts in the menus.

**Note that the project will open with errors!** That's because we
need to point Netbeans to the locations of some of the library
dependencies on your system; we will do this in the next step. **Just
ignore** the error warning for now (**click Close** in the popup in the popup window). 


Check out a new copy of the DVN source tree
---------------------------------------------------------

Create a new checkout of the DVN source tree. (That means, if you are
upgrading this development environment from DVN 2.2.\*, we strongly
recommend that you create a brand new source directory!)

Go to ``Team -> Subversion -> Checkout``; Use
`https://dvn.svn.sourceforge.net/svnroot/dvn/dvn-app/trunk <https://dvn.svn.sourceforge.net/svnroot/dvn/dvn-app/trunk>`__
for the Repository URL and your SourceForge username and password. Leave
the ``Client Certificate File`` and ``Passphrase`` fields blank.

When asked to choose the location for the checkout, enter something
like ``<SOME DIRECTORY>/dvn-app``. If you have an existing DVN
pre-3.0/NetBeans 6.\* development environment on this computer, make
sure this is a new directory, different from your old source tree.

On my system, my old source tree was in:
``/Users/leonid/NetBeansProjects/dvn-app``. I chose:
``/Users/leonid/NetBeansProjects\_7/dvn-app`` for the new checkout.

Once checked out, click “Yes” when prompted to open a project and
choose DVN-web (this is our main project now). Right click on the
project and select “Open Required Projects” - that will open DVN-Ingest
as well. DVN-EAR and DVN-EJB are no longer needed. The new project
settings that configure them for GlassFish 3.1 and Java EE6, have
already been committed to SourceForge, so (assuming it all works as it
should) you don’t have to further modify any settings there. 

Select Glassfish server
-----------------------------------

When prompted to choose from the list of available Glassfish servers,
make sure you choose the version 3.1.2, and not the version that came
with the NetBeans bundle. 

Configure NetBeans libraries
-----------------------------------------

Create the following 5 custom libraries using  ``Tools -> Libraries -> New Library``:

| ``dvn-lib-COMMON``
| ``dvn-lib-EJB``
| ``dvn-lib-WEB``
| ``dvn-lib-NETWORKDATA``
| ``dvn-lib-NETWORKDATA-EXTRA``

For each of these, simply select all the jar files from the directories respectively.

| ``<YOUR SVN ROOT>/trunk/lib/dvn-lib-COMMON``
| ``<YOUR SVN ROOT>/trunk/lib/dvn-lib-EJB``
| ``<YOUR SVN ROOT>/trunk/lib/dvn-lib-WEB``
| ``<YOUR SVN ROOT>/trunk/lib/dvn-lib-NetworkData``
| ``<YOUR SVN ROOT>/trunk/lib/dvn-lib-NetworkData-EXTRA``

Finally, add the following 5 JAR files in the Glassfish directory as
compile-time libraries to your project: 

In NetBeans, open the "Properties" menu of the DVN-web project; then
go to Libraries. Add the following 5 jar files, on at a time:


| ``common-util.jar``
| ``auto-depends.jar``
| ``grizzly-config.jar``
| ``internal-api.jar``
| ``config-api.jar``

by clicking on "Add JAR/Folder", then selecting each jar in the
.../glassfish/modules directory of your 3.1.2 installation. (For
example, ``/Applications/NetBeans/glassfish-3.1.2/glassfish/modules``).
Leave the "Package" box unchecked for each of these.

Build the application
-------------------------------

You should be ready to do “Clean and Build” of the main project,
DVN-web. If successful, it will produce the application
bundle ``DVN-web.war``.

Do not try to deploy the application just yet! We need to configure
the server environment first. This process is described in the next
chapter.  

Application Environment (Glassfish)
==============================================

In this chapter, we describe the process of setting up your own local
DVN server. You will be using it to deploy and test the DVN application,
once you compile and build it as described in Chapter I.

.. _automated-installer-new-in-v-3-0:

Automated Installer [NEW in v.3.0!]
----------------------------------------------------------

An automated script is now provided for installing and configuring Glassfish, Postgres and the DVN app, as part of a development
environment.

Before you run the installer, please install Postgres database server.

For the MacOS X (our default development OS), you can get the
installer here:
`http://www.postgresql.org/download/macosx <http://www.postgresql.org/download/macosx>`__.

The installation is very straightforward; just make sure you answer
"yes" when asked if Postgres should be accepting network connections.
(The application will be accessing the database at the "localhost"
address). 

Once installed, we recommend that you also allow connections
over local Unix sockets. This way the installer won't have to ask you
for the Postgres password every time it needs to talk to the database.
To do so, modify the "local all all" line in the data/pg\_hba.conf file
to look like this:

| local all all trust

**Note** that this only opens Postgres to the local socket connections,
and should not be considered a security risk. But if you are extra
cautious, you may use instead:

| local all all ident sameuser

Restart Postgres for the changes to take effect!

You can check the instructions in the main Installers Guide for more info:
:ref:`PostgreSQL section<postgresql>`;
but the above should be sufficient to get your environment set up.

The installer is supplied with the DVN source, in the tools directory of the SVN tree. You must run it as root (for direct access to
Postgres).

| To run the script:
| ``cd <YOUR SVN ROOT>/trunk/tools/installer/dvninstall``

| then execute
| ``./install-dev``

When prompted for various settings, you will likely be able to accept
all the default values (in a development environment, they are for the
most part the same for everybody).

Note: If the script above refuses to run, you may have to manually
turn the executable mode on:

``chmod +x install-dev``

Once this process is completed, you will have a fully functional
Dataverse Network server.

Manual Install
---------------------------------

For your reference, you can find the description of all the
configuration tasks performed by the installer script, with the
instructions for doing them manually, in the main DVN Installers Guide. 

DVN Developers Guide, v. 3.4-
+++++++++++++++++++++++++++++++++++++++

.. _build:

Build
=============

This is a walk through of building a war file from the DVN source code.
For information on deploying that war file to GlassFish, please see the
:ref:`deploy <deploy>` page.

Ensure you have a GitHub account
------------------------------------------------

Sign up at `https://github.com <https://github.com>`__

Please note that primary audience of this guide (for now) is people who
have push access to
`https://github.com/IQSS/dvn <https://github.com/IQSS/dvn>`__ . If you
do not have push access and want to contribute (and we hope you do!)
please fork the repo per
`https://help.github.com/articles/fork-a-repo <https://help.github.com/articles/fork-a-repo>`__
and make adjustments below when cloning the repo.

Set up an ssh keypair (if you haven't already)
-----------------------------------------------------

You *can* use git with passwords over HTTPS but it's much nicer to set
up SSH keys.

`https://github.com/settings/ssh <https://github.com/settings/ssh>`__ is
the place to manage the ssh keys GitHub knows about for you. That page
also links to a nice howto:
`https://help.github.com/articles/generating-ssh-keys <https://help.github.com/articles/generating-ssh-keys>`__

From the terminal, ``ssh-keygen`` will create new ssh keys for you:

-  private key: ``~/.ssh/id_rsa``

   -  It is **very important to protect your private key**. If someone
      else acquires it, they can access private repositories on GitHub
      and make commits as you! Ideally, you'll store your ssh keys on an
      encrypted volume and protect your private key with a password when
      prompted for one by ``ssh-keygen``. See also "Why do passphrases
      matter" at
      `https://help.github.com/articles/generating-ssh-keys <https://help.github.com/articles/generating-ssh-keys>`__

-  public key: ``~/.ssh/id_rsa.pub``

After you've created your ssh keys, add the public key to your GitHub
account.

Clone the repo
-----------------------------

Please see `branches <../branches/>`__ for detail, but in short, the
"develop" branch is where new commits go. Below we will assume you want
to make commits to "develop".

In NetBeans 7.1.1 or higher, click Team, then Git, then Clone.

Remote Repository
*************************************

-  Repository URL: ``github.com:IQSS/dvn.git``
-  Username: ``git``
-  Private/Public Key

   -  Private Key File: ``/Users/[YOUR_USERNAME]/.ssh/id_rsa``

-  Passphrase: (the passphrase you chose while running ``ssh-keygen``)

Click Next.

Remote Branches
*******************************

Under Select Remote Branches check both of these:

-  ``develop*``
-  ``master*``

Click Next.

Destination Directory
*******************************************

-  Parent Directory: ``/Users/[YOUR_USERNAME]/NetBeansProjects``
-  Clone Name: ``dvn``
-  Checkout Branch: ``develop*``

Click Finish.

You should see a message that the clone has completed and you will
probably be asked if you'd like to open a project. Click "Close" for now
and don't open a project.

Open the DVN-web and DVN-ingest projects
---------------------------------------------------

From the command line (show below) or otherwise, copy the
``project.properties`` and ``project.xml`` files into place for both the
"DVN-web" and "DVN-ingest" projects:

.. code-block:: guess

    murphy:~ pdurbin$ cd ~/NetBeansProjects/dvn/src/DVN-web/nbproject
    murphy:nbproject pdurbin$ cp project.properties.DIST project.properties
    murphy:nbproject pdurbin$ cp project.xml.DIST project.xml
    murphy:nbproject pdurbin$ cd ~
    murphy:~ pdurbin$ cd ~/NetBeansProjects/dvn/src/DVN-ingest/nbproject
    murphy:nbproject pdurbin$ cp project.properties.DIST project.properties
    murphy:nbproject pdurbin$ cp project.xml.DIST project.xml
    murphy:nbproject pdurbin$ 

Click Open Project. In NetBeansProjects select dvn, the src, then
DVN-web and Open Project.

Expect to see a dialog about reference problems. You can close this
dialog for now.

You may also see a dialog about missing server if you have not added a
GlassFish server in NetBeans yet.

Under Projects on the left you should now see DVN-web. Right-click it
and click Open Required Projects. This should open DVN-ingest. These two
projects are the only ones you need open for a build. If you open
additional projects (DVN-EAR, DVN-EJB, and DVN-lockss) you will likely
see build errors.

Install the ICEfaces plugin
-------------------------------------

Download
`http://dvn.iq.harvard.edu/dev/icefaces/ICEfaces-2.0.2-Netbeans-7.0.zip
<http://dvn.iq.harvard.edu/dev/icefaces/ICEfaces-2.0.2-Netbeans-7.0.zip>`__
and unzip it. Then click Tools, Plugins, Downloaded, Add Plugins, and
select all three nbm files.

Afterwards you'll need to fix one of the plugins:

-  Click Tools, then Ant Libraries
-  Click "ICEfaces Components (2.0.2)"
-  Click the red library
   (``nbinst://org.netbeans.libs.commons_logging/modules/ext/commons-logging-1.1.jar``)
   and click Remove
-  Click "Add JAR/folder" and add
   ``~/NetBeansProjects/dvn/lib/dvn-lib-WEB/commons-logging.jar`` (to
   replace the library you removed)

Configure NetBeans-wide Ant libraries
--------------------------------------------------

Create the following 5 custom libraries using Tools -> Ant Libraries ->
New Library:

-  dvn-lib-COMMON
-  dvn-lib-EJB
-  dvn-lib-WEB
-  dvn-lib-NETWORKDATA
-  dvn-lib-NETWORKDATA-EXTRA

For each of these, select all the jar files from the directories

-  lib/dvn-lib-COMMON
-  lib/dvn-lib-EJB
-  lib/dvn-lib-WEB
-  lib/dvn-lib-NetworkData
-  lib/dvn-lib-NetworkData-EXTRA

respectively.

Configure DVN-web project libraries
---------------------------------------------------

Under Projects, right-click DVN-web and choose "Resolve Reference
Problems". You should see the following jars listed:

-  auto-depends.jar
-  common-util.jar
-  config-api.jar
-  grizzly-config.jar
-  internal-api.jar

Highlight one of these jars and click Resolve. Then browse for the jar
in the glassfish/glassfish/modules directory of your GlassFish
installation. This *should* resolve the problem for all five jars above,
but if it doesn't, the rest of the jars can be found in the same
location.

Installing JUnit (if you haven't already)
---------------------------------------------------

Depending on how you installed NetBeans, you migtht already have JUnit
installed.

In the same "Resolve Reference Problems dialog" if you see problems with
junit or junit\_4, click Resolve and follow the prompts to install JUnit
from the NetBeans plugin portal.

GlassFish to add the server


Try a build
----------------------

At this point, under Projects, the DVN-web icon should no longer
indicate any errors and you can try a build. Hit F11 or click Run, then
Build Project.

If you get "BUILD SUCCESSFUL", you can proceed to the
:ref:`deploy <deploy>` step.

.. _deploy:

Deploy
=============

Once the :ref:`build <build>` of your war file is successful, you'll
want to deploy it to GlassFish.

For now, please see our older guide at:
:ref:`Automated Installer new in v3.0 <automated-installer-new-in-v-3-0>` for details.

download 

| `http://www.enterprisedb.com/products/pgdownload.do#osx <http://www.enterprisedb.com/products/pgdownload.do#osx>`__

via 

| `http://www.postgresql.org/download/macosx/ <http://www.postgresql.org/download/macosx/>`__

change $PATH for ``psql``

something about trust T

.. _commit:

Commit
==================

**Committing Changes**

By following the instructions in the :ref:`build <build>` step, you
should be in the "develop" branch, which is where we want to make
commits as we work toward the next release.

You can verify which branch you are on by clicking Team then "Repository
Browser".

You should see ``dvn [develop]`` at the root of the tree and **develop**
in bold under Branches -> Local

Click Team, then "Show Changes". Select the desired files and
right-click to commit.

To publish your changes on GitHub, you'll need to follow the next step:
:ref:`push <push>`.

.. _push:

Push
===========

**Pushing your commits to GitHub**

After making your :ref:`commit <commit>`, push it to GitHub by clicking Team -> Remote -> Push, then Next (to use your configured remote
repository), then checking **develop** and Finish.

Your commit should now appear on GitHub in the develop branch:
`https://github.com/IQSS/dvn/commits/develop <https://github.com/IQSS/dvn/commits/develop>`__

Your commit should **not** appear in the master branch on GitHub:
`https://github.com/IQSS/dvn/commits/master
<https://github.com/IQSS/dvn/commits/master>`__. Not yet anyway. Remember, we only merge commits into master when we are ready to release.


Release
============

Merge develop into master
--------------------------------------

Tag the release
***************************

Here is an example of how the 3.4 tag (
`https://github.com/IQSS/dvn/tree/3.4 <https://github.com/IQSS/dvn/tree/3.4>`__) was created and pushed to GitHub:

.. code-block:: guess

    murphy:dvn pdurbin$ git branch
    * develop
      master
    murphy:dvn pdurbin$ git pull
    Already up-to-date.
    murphy:dvn pdurbin$ git checkout master
    Switched to branch 'master'
    murphy:dvn pdurbin$ git merge develop
    Updating fdbfe57..6ceb24f
    (snip)
     create mode 100644 tools/installer/dvninstall/readme.md
    murphy:dvn pdurbin$ git tag
    3.3
    murphy:dvn pdurbin$ git tag -a 3.4 -m 'merged develop, tagging master as 3.4'
    murphy:dvn pdurbin$ git tag
    3.3
    3.4
    murphy:dvn pdurbin$ git push origin 3.4
    Counting objects: 1, done.
    Writing objects: 100% (1/1), 182 bytes, done.
    Total 1 (delta 0), reused 0 (delta 0)
    To git@github.com:IQSS/dvn.git
     * [new tag]         3.4 -> 3.4
    murphy:dvn pdurbin$ 
    murphy:dvn pdurbin$ git push
    Total 0 (delta 0), reused 0 (delta 0)
    To git@github.com:IQSS/dvn.git
       fdbfe57..6ceb24f  master -> master
    murphy:dvn pdurbin$ 

Make release available for download
******************************************************

On dvn-build:

.. code-block:: guess

    cd tools/installer
    mkdir dvninstall/config
    mkdir dvninstall/appdeploy/dist
    make installer

Rename the resulting "dvninstall.zip" to include the release number
(i.e. "dvninstall\_v3\_4.zip") and upload it, the separate war file, a
readme, and a buildupdate script (all these files should include the
release number) to SourceForge (i.e.
`http://sourceforge.net/projects/dvn/files/dvn/3.4/ <http://sourceforge.net/projects/dvn/files/dvn/3.4/>`__).

Increment the version number
*******************************************************

The file to edit is:

| `https://github.com/IQSS/dvn/blob/develop/src/DVN-web/src/VersionNumber.properties <https://github.com/IQSS/dvn/blob/develop/src/DVN-web/sr/VersionNumber.properties>`__

Branches
===========

Current list of branches
-------------------------------------

`https://github.com/IQSS/dvn/branches <https://github.com/IQSS/dvn/branches>`__

New branching model: develop vs. master
-------------------------------------------------

Please note that with the move to git, we are adopting the branching
model described at
`http://nvie.com/posts/a-successful-git-branching-model/ <http://nvie.com/posts/a-successful-git-branching-model/>`__

In this branching model there are two persistent branches:

-  develop: where all new commits go
-  master: where code gets merged and tagged as a release

That is to say, **please make your commits on the develop branch, not
the master branch**.

Feature branches
------------------------

    "The essence of a feature branch is that it exists as long as the
    feature is in development, but will eventually be merged back into
    develop (to definitely add the new feature to the upcoming release)
    or discarded (in case of a disappointing experiment)." --
    `http://nvie.com/posts/a-successful-git-branching-model/ <http://nvie.com/posts/a-successful-git-branching-model/>`__

Example feature branch: 2656-lucene
---------------------------------------------------

First, we create the branch and check it out:

::

    murphy:dvn pdurbin$ git branch
      2656-solr
    * develop
    murphy:dvn pdurbin$ git branch 2656-lucene
    murphy:dvn pdurbin$ 
    murphy:dvn pdurbin$ git branch
      2656-lucene
      2656-solr
    * develop
    murphy:dvn pdurbin$ git checkout 2656-lucene
    Switched to branch '2656-lucene'
    murphy:dvn pdurbin$ 
    murphy:dvn pdurbin$ git status
    # On branch 2656-lucene
    nothing to commit (working directory clean)
    murphy:dvn pdurbin$ 

| Then, we make a change and a commit, and push it to:

| `https://github.com/iqss/dvn/tree/2656-lucene <https://github.com/iqss/dvn/tree/2656-lucene>`__ (creating a new remote branch):


::

    murphy:dvn pdurbin$ vim src/DVN-EJB/src/java/edu/harvard/iq/dvn/core/index/Indexer.java
    murphy:dvn pdurbin$ 
    murphy:dvn pdurbin$ git commit -m 'start lucene faceting branch' src/DVN-EJB/src/java/edu/harvard/iq/dvn/core/index/Indexer.java
    [2656-lucene 3b82f88] start lucene faceting branch
     1 file changed, 73 insertions(+), 2 deletions(-)
    murphy:dvn pdurbin$ 
    murphy:dvn pdurbin$ git push origin 2656-lucene
    Counting objects: 25, done.
    Delta compression using up to 8 threads.
    Compressing objects: 100% (10/10), done.
    Writing objects: 100% (13/13), 2.23 KiB, done.
    Total 13 (delta 6), reused 0 (delta 0)
    To git@github.com:IQSS/dvn.git
     * [new branch]      2656-lucene -> 2656-lucene
    murphy:dvn pdurbin$ 

| 

As we work on the feature branch, we merge the latest changes from
"develop". We want to resolve conflicts in the feature branch itself so
that the feature branch will merge cleanly into "develop" when we're
ready. In the example below, we use ``git mergetool`` and ``opendiff``
to resolve conflicts and save the merge. Then we push the newly-merged
2656-lucene feature branch to GitHub:

| 

::

    murphy:dvn pdurbin$ git branch
    * 2656-lucene
      2656-solr
      develop
    murphy:dvn pdurbin$ git checkout develop
    murphy:dvn pdurbin$ git branch
      2656-lucene
      2656-solr
    * develop
    murphy:dvn pdurbin$ git pull
    remote: Counting objects: 206, done.
    remote: Compressing objects: 100% (43/43), done.
    remote: Total 120 (delta 70), reused 96 (delta 46)
    Receiving objects: 100% (120/120), 17.65 KiB, done.
    Resolving deltas: 100% (70/70), completed with 40 local objects.
    From github.com:IQSS/dvn
       8fd223d..9967413  develop    -> origin/develop
    Updating 8fd223d..9967413
    Fast-forward
     .../admin/EditNetworkPrivilegesServiceBean.java  |    5 +-
    (snip)
     src/DVN-web/web/study/StudyFilesFragment.xhtml   |    2 +-
     12 files changed, 203 insertions(+), 118 deletions(-)
    murphy:dvn pdurbin$ murphy:dvn pdurbin$ git pull
    remote: Counting objects: 206, done.
    remote: Compressing objects: 100% (43/43), done.
    remote: Total 120 (delta 70), reused 96 (delta 46)
    Receiving objects: 100% (120/120), 17.65 KiB, done.
    Resolving deltas: 100% (70/70), completed with 40 local objects.
    From github.com:IQSS/dvn
       8fd223d..9967413  develop    -> origin/develop
    Updating 8fd223d..9967413
    Fast-forward
     .../admin/EditNetworkPrivilegesServiceBean.java  |    5 +-
    (snip)
     .../harvard/iq/dvn/core/web/study/StudyUI.java   |    2 +-
     src/DVN-web/web/HomePage.xhtml                   |    5 +-
    murphy:dvn pdurbin$ 
    murphy:dvn pdurbin$ git checkout 2656-lucene
    Switched to branch '2656-lucene'
    murphy:dvn pdurbin$ 
    murphy:dvn pdurbin$ 
    murphy:dvn pdurbin$ git merge develop
    Auto-merging src/DVN-web/web/BasicSearchFragment.xhtml
    CONFLICT (content): Merge conflict in src/DVN-web/web/BasicSearchFragment.xhtml
    Auto-merging src/DVN-web/src/edu/harvard/iq/dvn/core/web/BasicSearchFragment.java
    Auto-merging src/DVN-EJB/src/java/edu/harvard/iq/dvn/core/index/Indexer.java
    Automatic merge failed; fix conflicts and then commit the result.
    murphy:dvn pdurbin$ 
    murphy:dvn pdurbin$ git status
    # On branch 2656-lucene
    # Changes to be committed:
    #
    #       modified:   src/DVN-EJB/src/java/edu/harvard/iq/dvn/core/admin/EditNetworkPrivilegesServiceBean.java
    (snip)
    #       new file:   src/DVN-web/web/admin/ChooseDataverseForCreateStudy.xhtml
    #       modified:   src/DVN-web/web/study/StudyFilesFragment.xhtml
    #
    # Unmerged paths:
    #   (use "git add/rm <file>..." as appropriate to mark resolution)
    #
    #       both modified:      src/DVN-web/web/BasicSearchFragment.xhtml
    #
    murphy:dvn pdurbin$ git mergetool
    merge tool candidates: opendiff kdiff3 tkdiff xxdiff meld tortoisemerge gvimdiff diffuse ecmerge p4merge araxis bc3 emerge vimdiff
    Merging:
    src/DVN-web/web/BasicSearchFragment.xhtml

    Normal merge conflict for 'src/DVN-web/web/BasicSearchFragment.xhtml':
      {local}: modified file
      {remote}: modified file
    Hit return to start merge resolution tool (opendiff):
    murphy:dvn pdurbin$ 
    murphy:dvn pdurbin$ git add .
    murphy:dvn pdurbin$ 
    murphy:dvn pdurbin$ git commit -m "Merge branch 'develop' into 2656-lucene"
    [2656-lucene 519cd8c] Merge branch 'develop' into 2656-lucene
    murphy:dvn pdurbin$ 
    murphy:dvn pdurbin$ git push origin 2656-lucene
    (snip)
    murphy:dvn pdurbin$ 


| When we are ready to merge the feature branch back into the develop branch, we can do so.

| Here's an example of merging the 2656-lucene branch back into develop:

::

    murphy:dvn pdurbin$ git checkout 2656-lucene
    Switched to branch '2656-lucene'
    murphy:dvn pdurbin$ git pull
    Already up-to-date.
    murphy:dvn pdurbin$ git checkout develop
    Switched to branch 'develop'
    murphy:dvn pdurbin$ git pull
    Already up-to-date.
    murphy:dvn pdurbin$ git merge 2656-lucene
    Removing lib/dvn-lib-EJB/lucene-core-3.0.0.jar
    Merge made by the 'recursive' strategy.
     lib/dvn-lib-EJB/lucene-core-3.0.0.jar                                     |  Bin 1021623 -> 0 bytes
     lib/dvn-lib-EJB/lucene-core-3.5.0.jar                                     |  Bin 0 -> 1466301 bytes
     lib/dvn-lib-EJB/lucene-facet-3.5.0.jar                                    |  Bin 0 -> 293582 bytes
     src/DVN-EJB/src/java/edu/harvard/iq/dvn/core/index/DvnQuery.java          |  160 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     src/DVN-EJB/src/java/edu/harvard/iq/dvn/core/index/IndexServiceBean.java  |   56 ++++++++++++++++++++
     src/DVN-EJB/src/java/edu/harvard/iq/dvn/core/index/IndexServiceLocal.java |   16 +++++-
     src/DVN-EJB/src/java/edu/harvard/iq/dvn/core/index/Indexer.java           |  432 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++--
     src/DVN-EJB/src/java/edu/harvard/iq/dvn/core/index/ResultsWithFacets.java |   71 +++++++++++++++++++++++++
     src/DVN-web/src/SearchFieldBundle.properties                              |    4 +-
     src/DVN-web/src/edu/harvard/iq/dvn/core/web/AdvSearchPage.java            |   86 +++++++++++++++++++++++++++++++
     src/DVN-web/src/edu/harvard/iq/dvn/core/web/BasicSearchFragment.java      |  102 +++++++++++++++++++++++++++++++++++-
     src/DVN-web/src/edu/harvard/iq/dvn/core/web/StudyListing.java             |   11 ++++
     src/DVN-web/src/edu/harvard/iq/dvn/core/web/StudyListingPage.java         |  428 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-
     src/DVN-web/src/edu/harvard/iq/dvn/core/web/study/FacetResultUI.java      |   42 +++++++++++++++
     src/DVN-web/src/edu/harvard/iq/dvn/core/web/study/FacetUI.java            |   62 ++++++++++++++++++++++
     src/DVN-web/web/AdvSearchPage.xhtml                                       |    3 +-
     src/DVN-web/web/BasicSearchFragment.xhtml                                 |    9 ++--
     src/DVN-web/web/StudyListingPage.xhtml                                    |   43 +++++++++++-----
     18 files changed, 1500 insertions(+), 25 deletions(-)
     delete mode 100644 lib/dvn-lib-EJB/lucene-core-3.0.0.jar
     create mode 100644 lib/dvn-lib-EJB/lucene-core-3.5.0.jar
     create mode 100644 lib/dvn-lib-EJB/lucene-facet-3.5.0.jar
     create mode 100644 src/DVN-EJB/src/java/edu/harvard/iq/dvn/core/index/DvnQuery.java
     create mode 100644 src/DVN-EJB/src/java/edu/harvard/iq/dvn/core/index/ResultsWithFacets.java
     create mode 100644 src/DVN-web/src/edu/harvard/iq/dvn/core/web/study/FacetResultUI.java
     create mode 100644 src/DVN-web/src/edu/harvard/iq/dvn/core/web/study/FacetUI.java
    murphy:dvn pdurbin$ 
    murphy:dvn pdurbin$ git status
    # On branch develop
    # Your branch is ahead of 'origin/develop' by 68 commits.
    #
    nothing to commit (working directory clean)
    murphy:dvn pdurbin$ 
    murphy:dvn pdurbin$ git push
    Counting objects: 51, done.
    Delta compression using up to 8 threads.
    Compressing objects: 100% (12/12), done.
    Writing objects: 100% (19/19), 1.41 KiB, done.
    Total 19 (delta 7), reused 0 (delta 0)
    To git@github.com:IQSS/dvn.git
       b7fae01..2b88b68  develop -> develop
    murphy:dvn pdurbin$ 

Switching to the master branch to merge commits from the develop branch
-------------------------------------------------------------------------------------------------------

We should really only need to switch from the develop branch to the
master branch as we prepare for a release.

First, we check out the master branch by clicking Team -> Git -> Branch
-> Switch to Branch.

Change Branch to "origin/master" and check the box for "Checkout as New
Branch" and fill in "master" as the "Branch Name" to match the name of
the branch we're switching to. Then click "Switch".

Now, in the Git Repository Browser (from Team -> Repository Browser) the
root of the tree should say ``dvn [master]`` and you should see two
branches under Branches -> Local. **master** should be in bold and
develop should not.

FIXME: explain how to merge commits into master for a final release (and
how to tag the release)

Features
========================

-  :ref:`API <api>`

  -  :ref:`Data Deposit <data-deposit-api>`

-  `Search <http://devguide.thedata.org/features/search/>`__


   -  `Faceted <http://devguide.thedata.org/features/search/faceted/>`__


      -  `Feedback <http://devguide.thedata.org/features/search/faceted/feedback/>`__
      -  `Roadmap <http://devguide.thedata.org/features/search/faceted/roadmap/>`__
      -  `Status <http://devguide.thedata.org/features/search/faceted/status/>`__
      -  `status.tsv <http://devguide.thedata.org/features/search/faceted/status.tsv>`__
      -  `Testing
         <http://devguide.thedata.org/features/search/faceted/testing/>`__

         -  `status.tsv <http://devguide.thedata.org/features/search/faceted/testing/status.tsv>`__


Tips
=========

Previewing changes before a pull
--------------------------------

If the build fails overnight you may want to hold off on doing a pull
until the problem is resolved. To preview what has changed since your
last pull, you can do a ``git fetch`` (the first part of a pull) then
``git log HEAD..origin/develop`` to see the commit messages.
``git log -p`` or ``git diff`` will allow you to see the contents of the
changes:

::

    git checkout develop
    git fetch
    git log HEAD..origin/develop
    git log -p HEAD..origin/develop
    git diff HEAD..origin/develop

After the build is working again, you can simply do a pull as normal.

Errors
===========

Unable to open DVN Web Project
-------------------------------------------

If you are seeing errors such as:

"Unable to find the sources roots for the project DVN-web"

or

"DVN-web: Cannot find the Web Pages folder. Open the project properties and in the Sources category browse the correct Web Pages folder"

you probably have lost your project.properties and project.xml files.
The :ref:`build <build>` page has instructions on putting them back
into place.

*Posted Thu May 2 13:33:33 2013*

Duplicate class
--------------------------

The error "duplicate class" can result whenever you resolve a merge
conflict in git.

The fix is to close NetBeans and delete (or move aside) the cache like
this:

::

    cd ~/Library/Caches/NetBeans
    mv 7.2.1 7.2.1.moved

According to
`https://netbeans.org/bugzilla/show_bug.cgi?id=197983 <https://netbeans.org/bugzilla/show_bug.cgi?id=197983>`__
this might be fixed in NetBeans 7.3.

*Posted Thu Apr 4 13:37:07 2013*