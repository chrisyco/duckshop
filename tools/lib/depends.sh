#!/bin/echo This is a library file and cannot be executed directly

if [ -z $CONFIG_SH ]
then
    echo "config.sh must be included first" >&2
    exit 42
fi

info() {
    echo $1 >&2
}

error() {
    echo "ERROR: $1" >&2
}

download() {
    url=$1
    info "Downloading $url..."
    file=$(basename $url)
    if [ -f $file ]
    then
        echo $file
        info "$file has already been downloaded, skipping"
        exit 0
    fi
    $WGET $url >>"$LOG" 2>&1
    if [ $? -eq 0 ]
    then
        echo $file
        info "... Done!"
    else
        error "Could not download file $url"
    fi
}

install() {
    info "Installing $1..."
    $MAVEN install:install-file -Dfile=$2 \
                         -DgroupId=$3 \
                         -DartifactId=$4 \
                         -Dversion=$5 \
                         -Dpackaging=jar >>"$LOG" 2>&1
    if [ $? -eq 0 ]
    then
        info "... Done!"
    else
        error "Could not install $1"
    fi
}
