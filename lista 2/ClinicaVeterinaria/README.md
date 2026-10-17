# Sistema de Clínica Veterinária

Código-fonte Java organizado nos pacotes `dados`, `negocio` e `Sistema`.

Compile a partir desta pasta:

```text
javac -encoding UTF-8 -d out src/dados/*.java src/negocio/*.java src/Sistema/*.java
```

Execute com `java -cp out Sistema.Principal`. Informe datas como `AAAA-MM-DD` e horários como `HH:MM`. A listagem de consultas de cada data é ordenada pelo horário.
