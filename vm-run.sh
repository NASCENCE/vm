#!/bin/bash
java -cp lib/libthrift-1.0.0.jar:lib/log4j-1.2-1.2.16.jar:lib/slf4j-log4j12-1.6.5.jar:lib/slf4j-api-1.6.5.jar:dist/nascence-api.jar:dist/virtual-material.jar nascence.vm.thrift.VirtualMaterialServer
