#!/usr/bin/env bash
# define  global variable
# file name
filename=$(basename $0)

ipwd=$(
  cd $(dirname $0)
  pwd
)

checkEnvArgs() {
  unames=$(uname -s)
  local cygwin="CYGWIN"
  local mingw="MINGW"
  local msys_nt="MSYS_NT"
  local macos="Darwin"
  local linux="Linux"
  support_printf_os=""
  if [ "$(echo $unames | grep "$cygwin")" != "" ]; then
    echo "[$filename] your platform is win . cygwin"
    red='\e[0;31m'
    green='\e[0;32m'
    yellow='\e[0;33m'
    blue='\e[0;34m'
    endColor='\e[0m'
    ecs="echo -e"
    support_printf_os=""
  elif [ "$(echo $unames | grep "$mingw")" != "" ]; then
    echo "[$filename] your platform is win . mingw"
    red='\033[31m'
    green='\033[32m'
    yellow='\033[33m'
    blue='\033[34m'
    endColor='\033[0m'
    ecs="echo -e"
    support_printf_os=""
  elif [ "$(echo $unames | grep "$msys_nt")" != "" ]; then
    echo "[$filename] your platform is win10 . mingw"
    red='\e[0;31m'
    green='\e[0;32m'
    yellow='\e[0;33m'
    blue='\e[0;34m'
    endColor='\e[0m'
    ecs="echo -e"
    support_printf_os=""
  elif [ "$(echo $unames | grep "$macos")" != "" ]; then
    echo "[$filename] your platform is macos"
    red='\033[31m'
    green='\033[32m'
    yellow='\033[33m'
    blue='\033[34m'
    endColor='\033[0m'
    ecs="printf"
    support_printf_os="macos"
  elif [ "$(echo $unames | grep "$linux")" != "" ]; then
    echo "[$filename] your platform is $linux"
    red='\033[31m'
    green='\033[32m'
    yellow='\033[33m'
    blue='\033[34m'
    endColor='\033[0m'
    ecs="printf"
    support_printf_os="$linux"
  else
    echo "[$filename]your platform is $unames"
    red='\033[31m'
    green='\033[32m'
    yellow='\033[33m'
    endColor='\033[0m'
    ecs="echo"
    support_printf_os=""
  fi
  curtime=$(date "+%Y-%m-%d %H:%M:%S")
}

mdout_init() {
  $mdout install
}

# make sure env
makesureEnv() {
  if [ "$curtime" = "" ]; then
    checkEnvArgs
  fi
}
logd() {
  makesureEnv
  if [ "$1" ] && [ ! "$support_printf_os" ]; then
    $ecs "${blue}$1${endColor}"
  else
    $ecs "${blue}$1${endColor}\n"
  fi
}
logi() {
  makesureEnv
  if [ "$1" ] && [ ! "$support_printf_os" ]; then
    $ecs "${green}$1${endColor}"
  else
    $ecs "${green}$1${endColor}\n"
  fi
}
loge() {
  makesureEnv
  if [ "$1" ] && [ ! "$support_printf_os" ]; then
    $ecs "${red}$1${endColor}"
  else
    $ecs "${red}$1${endColor}\n"
  fi
}
logw() {
  makesureEnv
  if [ "$1" ] && [ ! "$support_printf_os" ]; then
    $ecs "${yellow}$1${endColor}"
  else
    $ecs "${yellow}$1${endColor}\n"
  fi
}
test() {
  logd "test log"
  logi "test log"
  loge "test log"
  logw "test log"
}
#main() {
#    makesureEnv
#    mdout_init
#    test
#    logi $mdout
#}
#
### call method
#main

#logd $0
#logd $1
if [ -n "$1" ]; then
  logi "has one args"
  chmod -R 777 tools/
  $mdout install
  git config core.filemode false
fi
