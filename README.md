# Trabajo Práctico - Juego Mafia

## Informe

El informe completo del proyecto se encuentra en Overleaf:

https://es.overleaf.com/read/hfjgbbcfzhxn#7adf60


## Ejecución de pruebas

### Toda la suite
mvn test

### Por archivo
mvn test -Dtest=TestVotacionDiurna
Varios archivos separados por coma, y se admiten comodines:
mvn test "-Dtest=TestVotacionDiurna,TestVotacionMafia"
mvn test "-Dtest=TestFase*"
Un único método:
mvn test -Dtest=TestVotacionDiurna#elMasVotadoEsElUnicoGanadorPorMayoria

### Por tag (grupo)
mvn test -Dgroups=padrino
Excluir un grupo (por ejemplo, las pruebas con pausas de la demo en CI):
mvn test -DexcludedGroups=demo

#### Tags disponibles
| Categoría | Tags |
|-----------|------|
| Rol       | `padrino`, `detective`, `medico`, `sheriff`, `mafioso`, `ciudadano` |
| Área      | `votacion`, `victoria`, `reparto`, `fases`, `registro`, `carta`, `ballotage` |
| Nivel     | `integracion`, `simularJuego`, `demo` |

Un test puede tener varios tags (p. ej. el voto prioritario del Padrino es `votacion` + `padrino`).