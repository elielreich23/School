# Requisitos — Sistema de Gestão de Escola de Idiomas

## 1. Objetivo e escopo

Desenvolver, em Java, as camadas de dados e de negócio de um sistema para uma escola de idiomas. Na Etapa I, os dados serão mantidos em listas de objetos gerenciadas pela classe `Sistema`; não fazem parte desta etapa a interface gráfica nem a persistência em banco de dados.

O sistema deve apoiar o cadastro e a consulta das informações acadêmicas e financeiras descritas neste documento, além de permitir testar suas operações por meio de uma classe com `main()`.

## 2. Atores

- **Administrador/atendente:** mantém cadastros, matrículas, turmas, pagamentos e vendas.
- **Professor:** tem seus dados e formação cadastrados; pode ministrar cursos e registrar avaliações, notas, observações e frequência dos alunos.
- **Aluno:** pode estar matriculado em turmas e realizar pagamentos, avaliações, aulas particulares e compras de materiais.

Os atores servem para delimitar o domínio. Autenticação e permissões de acesso não estão especificadas no enunciado e ficam fora do escopo desta etapa.

## 3. Requisitos funcionais

### Cadastros acadêmicos

- **RF01 — Cursos:** cadastrar, consultar, atualizar e remover cursos com código, idioma, nível (básico, intermediário ou avançado), carga horária total, descrição do conteúdo programático e materiais didáticos necessários.
- **RF02 — Módulos:** cadastrar módulos sequenciais de um curso, com número, nome, carga horária, horário de aprendizagem e pré-requisitos. Consultar os módulos na sequência definida para o curso.
- **RF03 — Professores:** cadastrar, consultar, atualizar e remover professores com nome, CPF, telefone, e-mail, data de contratação, formação acadêmica e idiomas ministrados com respectivos níveis de proficiência.
- **RF04 — Alunos:** cadastrar, consultar, atualizar e remover alunos com nome, CPF, data de nascimento, endereço, telefone, e-mail e nível de conhecimento em cada idioma de interesse.
- **RF05 — Turmas:** cadastrar turmas associadas a um curso e a um professor responsável, com período de matrícula, quantidade mínima e máxima de alunos e situação (aberta para matrícula, em andamento, concluída ou cancelada).
- **RF06 — Horários das turmas:** registrar um ou mais encontros por turma, incluindo dia da semana, horário de início, horário de término e sala.
- **RF07 — Matrículas:** matricular um aluno em uma turma e registrar data da matrícula, mensalidade, quantidade de parcelas, desconto aplicável e forma de pagamento. Consultar as matrículas por aluno ou turma e cancelar uma matrícula.
- **RF08 — Limites de turma:** permitir matrícula somente em turma aberta, respeitando o limite máximo. A turma não pode iniciar com menos alunos que o mínimo definido; o sistema deve permitir consultar se atingiu esse mínimo.

### Financeiro

- **RF09 — Mensalidades:** gerar, para cada matrícula, mensalidades numeradas com data de vencimento, valor, data de pagamento e situação (pendente, paga ou vencida).
- **RF10 — Pagamentos:** registrar o pagamento integral de uma mensalidade pelo valor devido na data do pagamento, incluindo data e forma utilizada; consultar as mensalidades e pagamentos de uma matrícula. Pagamentos parciais não quitam a mensalidade.
- **RF11 — Atrasos:** calcular multa e juros de pagamentos em atraso conforme parâmetros configuráveis pelo sistema. O cálculo deve poder ser consultado sem perder o valor original da mensalidade.
- **RF12 — Materiais didáticos:** cadastrar materiais com código, título, editora, edição, tipo e preço.
- **RF13 — Vendas:** registrar separadamente das mensalidades a venda de materiais a um aluno, contendo data, itens, quantidades e valores praticados; consultar vendas por aluno.

### Acompanhamento pedagógico

- **RF14 — Avaliações:** cadastrar avaliações vinculadas a um módulo com tipo (prova escrita, prova oral, trabalho ou exercício), data de aplicação, valor máximo de pontos e peso na média final.
- **RF15 — Notas:** registrar, para cada aluno matriculado, a pontuação obtida em cada avaliação e eventuais observações do professor; consultar as notas do aluno.
- **RF16 — Média final:** calcular a média final do aluno no módulo usando as avaliações cadastradas e seus pesos. A regra de arredondamento e a escala da média devem ser definidas como parâmetros do sistema.
- **RF17 — Frequência:** registrar presença ou falta do aluno em cada encontro da turma, vinculado a um módulo; cada aluno pode ter um único registro por encontro. Calcular a frequência percentual por módulo; sem registros de presença, a frequência é zero.
- **RF18 — Aulas particulares:** agendar aulas particulares com aluno, professor, data, horário de início, horário de término, conteúdo ministrado e observações; consultar aulas por aluno ou professor.
- **RF19 — Certificados:** registrar a conclusão de um módulo e emitir/consultar certificado contendo número, data de emissão, aluno, curso, módulo, carga horária e nota final.

## 4. Regras de negócio

- **RN01:** códigos de curso e material, número de certificado e CPF de aluno/professor devem ser únicos nos respectivos cadastros.
- **RN02:** um módulo pertence a um curso e sua numeração determina a sequência; pré-requisitos devem apontar para módulos válidos.
- **RN03:** o professor responsável por uma turma deve estar habilitado a ministrar o idioma e o nível do curso, conforme sua formação/proficiência cadastrada.
- **RN04:** aluno só pode ser matriculado em turma aberta no período de matrícula; turma cancelada ou concluída não aceita novas matrículas.
- **RN05:** uma turma não pode exceder o máximo de alunos. Atingir o mínimo é condição para iniciar, conforme RF08.
- **RN06:** cada matrícula gera a quantidade de mensalidades/parcelas informada. Valor, vencimento e situação devem ser válidos; uma mensalidade paga não pode ser paga novamente. Ao cancelar uma matrícula, mensalidades ainda não pagas passam para a situação cancelada, preservando o histórico.
- **RN07:** multa e juros incidem apenas sobre mensalidade vencida e ainda não paga. As taxas e a regra exata de cálculo precisam ser parametrizáveis, pois o enunciado não informa percentuais nem fórmula.
- **RN08:** pontuação de avaliação deve estar entre zero e o valor máximo; peso deve ser positivo. Cada avaliação aceita no máximo uma nota por matrícula, e a avaliação deve pertencer ao curso da matrícula. A média considera os pesos das avaliações aplicáveis.
- **RN09:** frequência percentual por módulo é calculada pela proporção de presenças sobre encontros daquele módulo com frequência registrada; encontros sem registro não entram no cálculo. Não se pode lançar frequência em encontro de outra turma nem duplicar registro.
- **RN10:** conclusão e emissão do certificado dependem da aprovação do aluno no módulo e de esse módulo pertencer ao curso da matrícula. A frequência usada é a do módulo; não se emite certificado duplicado para o mesmo aluno e módulo. O enunciado não define nota mínima nem frequência mínima, portanto esses limites devem ser parâmetros configuráveis.
- **RN11:** venda de material é um registro financeiro separado das mensalidades e deve preservar preço e quantidade praticados na venda.
- **RN12:** operações de remoção devem preservar a consistência dos relacionamentos. Registros com histórico (matrículas, pagamentos, notas, frequência, vendas e certificados) devem ser cancelados/inativados ou ter remoção impedida, em vez de apagar o histórico.

## 5. Modelo conceitual inicial

O diagrama de classes da Etapa I deve conter os pacotes `dados` e `negocio`. O pacote `negocio` contém `Sistema`, que mantém e manipula listas das classes de `dados`.

Classes de dados sugeridas a partir do enunciado:

- `Curso`, `Modulo`, `Professor`, `IdiomaProfissional` (idioma e proficiência), `Aluno`, `IdiomaInteresse` (idioma e nível), `Turma`, `HorarioTurma`, `Matricula`, `Mensalidade`, `Pagamento`, `Avaliacao`, `Nota`, `RegistroFrequencia`, `AulaParticular`, `Certificado`, `Material`, `Venda` e `ItemVenda`.

Relacionamentos centrais: curso agrega módulos; turma pertence a um curso e tem um professor; aluno e turma se relacionam por matrícula; matrícula possui mensalidades; mensalidade pode ter pagamento; módulo/curso possui avaliações; nota relaciona aluno matriculado e avaliação; frequência relaciona aluno matriculado e encontro; certificado relaciona aluno, curso e módulo; venda pertence a aluno e contém itens de materiais.

As cardinalidades e a decisão entre composição, associação e classes de associação devem ser confirmadas no diagrama antes da implementação. `Sistema` deve oferecer operações para os requisitos acima, com validações e consultas; não deve concentrar os dados em campos duplicados quando uma associação entre classes representar melhor o domínio.

## 6. Critérios de aceitação da Etapa I

- O diagrama UML apresenta os pacotes `dados` e `negocio`, a classe `Sistema`, as classes de dados necessárias, relacionamentos e cardinalidades.
- As classes Java correspondem ao diagrama e encapsulam seus atributos.
- `Sistema` gerencia coleções em memória e implementa as operações funcionais previstas para a etapa.
- As operações rejeitam dados inválidos e preservam as regras de negócio e os relacionamentos.
- Uma classe com `main()` demonstra os principais fluxos: cadastro de curso/módulos, professor, aluno, turma, matrícula, geração e pagamento de mensalidade, avaliação/nota, frequência e certificado, além de venda de material.

## 7. Decisões pendentes para fechar antes do diagrama

O enunciado não especifica: percentuais/fórmula de multa e juros; nota e frequência mínimas para aprovação; regra de arredondamento da média; datas e regra de geração dos vencimentos; se desconto é percentual ou valor fixo; se uma turma pode cobrir vários módulos; nem regras de conflito de horário de professor/sala. Até essas decisões serem tomadas, os respectivos valores devem ser configuráveis e as regras não devem ser fixadas arbitrariamente no código.
