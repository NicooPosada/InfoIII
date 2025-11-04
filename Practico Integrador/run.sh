#!/bin/bash

# Script para compilar y ejecutar el sistema de turnos médicos

echo "======================================"
echo "Compilando sistema de turnos médicos"
echo "======================================"

cd "/home/fabricio-posada/InfoIII/Practico Integrador"

# Compilar todos los archivos Java
javac Paciente.java Medico.java Turno.java SistemaTurnos.java Main.java

if [ $? -eq 0 ]; then
    echo "✓ Compilación exitosa"
    echo ""
    echo "======================================"
    echo "Ejecutando el sistema..."
    echo "======================================"
    echo ""
    java Main
else
    echo " Error en la compilación"
fi
