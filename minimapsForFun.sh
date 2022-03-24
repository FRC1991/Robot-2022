#!/bin/zsh

# this is a script for Aryamaan, specific to his system to generate the code previews in the "minimaps images" folder
# he quickly threw it together, so it's not pretty, but it works
# if you're anyone else, simply ignore this file, or edit it to make it work for anyone
# it uses https://github.com/Ivoah/minimap

for file in ./src/main/java/frc/**/*.java
do
minimap=/home/arya/miniconda3/bin/minimap
filename=$(basename $file .java)
$minimap $file -s one-dark -o minimaps/$filename.png --overwrite
done
