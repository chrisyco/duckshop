#!/bin/sh

. $(dirname $0)/lib/config.sh
. $(dirname $0)/lib/depends.sh

mkdir -p $TMPDIR
cd $TMPDIR

echo "Chris Wong's Super Awesome Dependency Downloader!" | tee "$LOG"

#name=$(download 'http://ci.bukkit.org/job/dev-Bukkit/lastSuccessfulBuild/artifact/target/bukkit-0.0.1-SNAPSHOT.jar')
#install "Bukkit" $name org.bukkit bukkit 0.0.1-SNAPSHOT

name=$(download 'https://github.com/iConomy/Register/raw/master/dist/Register.jar')
install "Register" $name com.iConomy Register LATEST

name=$(download 'http://www.theyeticave.net/downloads/permissions/3.1.5/Permissions.jar')
install "Permissions" $name com.nijikokun.bukkit Permissions 3.1.5
