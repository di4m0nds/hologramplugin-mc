#!/bin/bash

# Check if the script is being run from the plugin root directory
if [ ! -f "ex.sh" ]; then
    echo "Error: Please run this script from the plugin root directory."
    exit 1
fi

echo "Compiling plugin..."

# Compile Java source files using the Spigot API
javac -cp ../.lib/spigot-api-1.20.1.jar -d bin \
    ./src/main/java/com/di4m0nds/hologramplugin/HologramPlugin.java \
    ./src/main/java/com/di4m0nds/hologramplugin/commands/CreateHologramCommand.java \
    ./src/main/java/com/di4m0nds/hologramplugin/commands/RemoveHologramCommand.java \
    ./src/main/java/com/di4m0nds/hologramplugin/utils/HologramUtils.java \
    ./src/main/java/com/di4m0nds/hologramplugin/listeners/HologramClickListener.java

# Maven Build
mvn clean install

# Display the contents of the JAR file
jar tf ./target/hologramplugin-1.0.0-SNAPSHOT.jar

# Update the plugin.yml file in the JAR
jar uf ./target/hologramplugin-1.0.0-SNAPSHOT.jar ./plugin.yml

# Generate JAR without Maven
cd bin/
jar cf HologramPlugin.jar com
jar uf HologramPlugin.jar ../plugin.yml
cd ..

# Include the new plugin in the Spigot server (MAVEN way)
cp ./target/hologramplugin-1.0.0-SNAPSHOT.jar ../../spigot-server/plugins/HologramPlugin-1.0.0-SNAPSHOT.jar

# Include the new plugin in the Spigot server (without MAVEN)
# cp ./bin/HologramPlugin.jar ../../spigot-server/plugins/HologramPlugin.jar

# Start the Spigot server
cd ..
cd ..
cd spigot-server/
./start-spigot.sh

echo "Finished Process"
