# Roteiro para apresentar a interface gráfica

Boa tarde, professor. Vou apresentar a implementação da interface gráfica do sistema de gestão de uma escola de idiomas.

A interface foi implementada em Java usando **Swing**, que já faz parte do JDK. A ideia foi organizar as funcionalidades por áreas, para que o usuário possa navegar pelos cadastros e operações sem abrir uma janela diferente para cada tela.

## Organização do projeto

A interface fica no pacote apresentacao. A classe principal é InterfaceGrafica, que contém o método main() e cria a janela da aplicação. O ponto de entrada da interface é apresentacao.InterfaceGrafica.main(). A classe negocio.Programa é outra entrada: ela executa a demonstração de regras da Etapa I pelo console.

A estrutura segue a separação em camadas do projeto:

    InterfaceGrafica e componentes Swing
                  ↓
            negocio.Sistema
                  ↓
        Entidades do pacote dados

A classe InterfaceGrafica cuida da apresentação e das interações. Quando o usuário cadastra, altera ou consulta informações, a interface chama métodos da classe Sistema, que aplica as regras de negócio. Os objetos manipulados são classes do pacote dados, como Curso, Aluno, Turma, Matricula e Pagamento.

Nesta etapa, os dados ficam **em memória** nas listas controladas por Sistema. A interface ainda não usa banco de dados; essa persistência pertence à Etapa III do trabalho.

## Inicialização e navegação

O método main() inicia a criação da janela dentro de SwingUtilities.invokeLater. Isso faz com que a interface seja construída na fila de eventos do Swing, que é o fluxo adequado para atualizar componentes gráficos.

O construtor de InterfaceGrafica prepara o visual, configura o tamanho da janela, constrói as seções e atualiza as tabelas. Para que a janela se adapte à tela, o método configurarJanela() consulta o espaço disponível no monitor e limita o tamanho inicial da aplicação.

A navegação usa CardLayout. Cada tela é adicionada ao painel principal com uma chave, como "alunos", "cursos" ou "matriculas". Quando o usuário escolhe uma opção no menu, o método navegarPara() mostra o painel correspondente. Essa organização mantém uma janela principal, com o menu lateral e o conteúdo da seção selecionada.

As opções estão agrupadas por áreas, como acadêmico, pessoas, secretaria, pedagógico e financeiro. O painel lateral tem rolagem para continuar acessível quando a janela é menor. O painel do dashboard também pode rolar verticalmente, para que seus dados não fiquem cortados.

## Telas e funcionalidades

A tela inicial é o **dashboard**, criado em criarSecaoDashboard(). Ela apresenta indicadores resumidos, como quantidade de alunos, professores, cursos, turmas, matrículas, mensalidades, pagamentos, certificados e vendas. Também contém atalhos para algumas operações frequentes.

As demais seções são criadas por métodos específicos, como criarSecaoCursos(), criarSecaoAlunos() e criarSecaoTurmas(). No total, a interface organiza:

- cursos e módulos;
- turmas, horários e encontros;
- alunos e professores;
- matrículas e mensalidades;
- aulas particulares e certificados;
- avaliações, notas e frequência;
- pagamentos, materiais e vendas;
- parâmetros financeiros e acadêmicos.

Cada tela apresenta uma tabela com os dados correspondentes e ações relacionadas àquela área. Por exemplo, a tela de cursos permite cadastrar, editar e remover cursos, adicionar módulos e consultar os módulos de um curso. A tela de turmas permite criar turmas, adicionar horários, agendar encontros, iniciar uma turma e consultar sua agenda.

## Tabelas, busca e atualização

As tabelas são feitas com JTable e DefaultTableModel. O modelo de cada tabela é configurado para não permitir edição direta das células. Assim, as alterações passam pelos formulários e pelas regras do Sistema, em vez de modificar os dados sem validação na tabela.

Cada seção registra uma função de atualização na lista atualizadores. O método atualizarTodasTabelas() executa essas funções para reconstruir as linhas a partir das listas consultadas em Sistema. Isso é chamado ao navegar pelas seções e depois de uma operação, mantendo a tela sincronizada com os dados.

O método montarPainelCRUD() reaproveita a estrutura comum das telas de cadastro: título, descrição, campo de busca, botões de ação e tabela com rolagem. A busca usa TableRowSorter e RowFilter; o texto é pesquisado sem diferenciar maiúsculas e minúsculas, e Pattern.quote() evita interpretar o que o usuário digitou como uma expressão regular.

## Formulários e validações

Os formulários são construídos pela classe UIFormDialog, uma janela modal reutilizável. Ela organiza os campos com GridBagLayout e oferece métodos para incluir diferentes tipos de entrada:

- addTextField() para texto;
- addTextArea() para descrições e observações;
- addComboBox() para opções definidas, como nível, curso, aluno ou forma de pagamento;
- addIntegerSpinner() para quantidades e carga horária;
- addDecimalField() para valores e notas;
- addDateField() para datas;
- addTimeField() para horários;
- addCheckBox() para opções de presença.

O método adicionarLinha() posiciona cada campo e registra sua chave, rótulo e se é obrigatório. Ao confirmar o formulário, validarESubmeter() verifica campos obrigatórios e formatos de números, datas e horários. Se houver erro, o formulário apresenta uma mensagem e permanece aberto para correção. Se o usuário cancelar, showDialog() retorna false, e a operação é interrompida.

Depois da confirmação, métodos como novoCurso(), novoAluno() ou matricular() recuperam os valores pelos métodos getText(), getInteger(), getBigDecimal(), getDate() e getSelected(). Em seguida, constroem o objeto necessário e chamam o método apropriado de Sistema.

Por exemplo, ao cadastrar um curso, novoCurso() monta um objeto Curso com os dados do formulário e chama sistema.cadastrarCurso(curso). Para matricular um aluno, matricular() permite selecionar aluno e turma, coleta os dados financeiros e chama sistema.matricular(...). Essa chamada centraliza as validações e pode gerar as mensalidades previstas pela regra de negócio.

## Estilo e componentes auxiliares

A classe UITheme concentra escolhas visuais que são reutilizadas, como fontes, cores, botões, cabeçalhos, cartões, configuração de tabelas e formatação de CPF, datas, horários e valores monetários. Isso ajuda a manter as telas consistentes.

A classe StatusBadgeRenderer personaliza a forma como estados aparecem nas tabelas, por exemplo a situação de uma turma ou mensalidade. Ela altera a apresentação do texto, mas não muda a regra de negócio.

A classe TableDetailDialog exibe detalhes em uma janela modal com tabela, como módulos, horários ou mensalidades. Ela também oferece exibirTextoFormatado() para mostrar informações em formato de texto.

## Tratamento de erros e encerramento

As ações das telas são encaminhadas por executar(). Esse método chama a operação, atualiza as tabelas e mostra uma mensagem de sucesso. Se ocorrer uma exceção de regra de negócio ou entrada inválida, ele apresenta uma mensagem para o usuário. As operações canceladas são tratadas separadamente para não serem exibidas como erros.

Durante a verificação, também foi corrigido o cadastro de avaliações: cadastrarAvaliacao() agora adiciona a avaliação à lista do sistema, além de vinculá-la ao módulo. Isso era necessário para que o registro de notas encontrasse a avaliação cadastrada.

A implementação foi compilada, a demonstração da Etapa I terminou com sucesso e a interface permaneceu executando quando iniciada por apresentacao.InterfaceGrafica.main().

## Resumo das classes principais

| Classe | Responsabilidade |
|---|---|
| InterfaceGrafica | Cria a janela, navegação, seções, tabelas e aciona operações de negócio. |
| UIFormDialog | Cria formulários modais com componentes adequados e validação de entrada. |
| UITheme | Centraliza estilos, componentes reutilizáveis e formatação de dados. |
| TableDetailDialog | Mostra consultas detalhadas em tabelas ou textos. |
| StatusBadgeRenderer | Apresenta estados de objetos de forma destacada nas tabelas. |
| Sistema | Mantém os dados em memória e aplica as regras e operações do sistema. |
| Classes de dados | Representam entidades, como cursos, alunos, turmas, matrículas e pagamentos. |

## Como iniciar a interface

Na IDE, execute o método main() da classe apresentacao.InterfaceGrafica. A classe negocio.Programa é destinada à demonstração da Etapa I pelo console.

## Relação entre telas, funções e operações

Esta tabela pode ser usada como consulta rápida durante a apresentação. Os métodos criarSecao... montam cada painel, enquanto os métodos de ação coletam os dados e chamam Sistema.

| Tela | Funções de interface mais importantes | O que o usuário faz |
|---|---|---|
| Visão geral | criarSecaoDashboard(), carregarDadosDemonstracao() | Consulta indicadores e acessa atalhos; pode carregar dados de exemplo quando o sistema está vazio. |
| Cursos e módulos | criarSecaoCursos(), novoCurso(), editarCurso(), removerCurso(), novoModulo(), verModulos() | Mantém cursos e módulos, incluindo a ordem e os pré-requisitos entre módulos. |
| Turmas e horários | criarSecaoTurmas(), novaTurma(), adicionarHorario(), adicionarEncontro(), iniciarTurma(), verHorariosEEncontrosDaTurma() | Cria uma turma ligada a curso e professor, define vagas e período, agenda horários/encontros e consulta a agenda. |
| Alunos | criarSecaoAlunos(), novoAluno(), editarAluno(), removerAluno(), exibirHistoricoCompletoAluno() | Mantém cadastro e interesses linguísticos e permite consultar o histórico do estudante. |
| Professores | criarSecaoProfessores(), novoProfessor(), editarProfessor(), removerProfessor() | Mantém dados pessoais, formação e qualificações de idioma e nível. |
| Matrículas | criarSecaoMatriculas(), matricular(), cancelarMatricula(), matriculasPorAluno(), matriculasPorTurma() | Matricula um aluno em turma aberta, cancela matrícula e consulta por aluno ou turma. |
| Aulas particulares | criarSecaoAulasParticulares(), agendarAula(), aulasDoAluno(), aulasDoProfessor() | Agenda uma aula entre aluno e professor e consulta os agendamentos relacionados a cada pessoa. |
| Certificados | criarSecaoCertificados(), emitirCertificado(), certificadosDoAluno() | Solicita a conclusão do módulo, que é validada pelo Sistema, e consulta certificados por aluno. |
| Avaliações | criarSecaoAvaliacoes(), novaAvaliacao() | Cadastra avaliações ligadas a um módulo, com tipo, data, valor máximo e peso. |
| Notas | criarSecaoNotas(), registrarNota(), calcularMedia() | Registra pontuações e observações e consulta a média ponderada de um módulo. |
| Frequência | criarSecaoFrequencia(), registrarFrequencia(), calcularFrequencia() | Marca presença ou falta em um encontro e calcula a frequência do aluno. |
| Mensalidades | criarSecaoMensalidades(), calcularAtraso(), registrarPagamento(), mensalidadesDaMatricula() | Consulta parcelas, calcula encargos de atraso, registra pagamento e lista mensalidades de uma matrícula. |
| Pagamentos | criarSecaoPagamentos() | Consulta os pagamentos já registrados em uma tabela. O registro do pagamento é iniciado pela área de mensalidades. |
| Materiais | criarSecaoMateriais(), novoMaterial() | Cadastra materiais didáticos com código, título, editora, edição, tipo e preço. |
| Vendas | criarSecaoVendas(), registrarVenda(), vendasDoAluno() | Registra a venda de materiais vinculada a um aluno e consulta vendas por aluno. |
| Parâmetros | criarSecaoParametros() | Consulta e altera taxas financeiras e critérios acadêmicos usados nas validações e cálculos do Sistema. |

## Fluxo de uma operação

Um exemplo simples para explicar a comunicação entre as camadas é o cadastro de curso:

1. O usuário abre a seção de cursos e escolhe a ação de cadastrar.
2. O botão executa novoCurso() através do método executar(), que centraliza atualização e tratamento de erros.
3. novoCurso() monta um UIFormDialog e adiciona campos de código, idioma, nível, carga horária, descrição e material.
4. O formulário valida os dados obrigatórios e seus formatos. Se for cancelado, o cadastro para sem alterar o sistema.
5. Após a confirmação, InterfaceGrafica constrói um objeto Curso e chama sistema.cadastrarCurso(curso).
6. Sistema verifica as regras de negócio e adiciona o objeto às suas listas em memória.
7. executar() atualiza as tabelas, então a nova linha aparece na tela.

Esse mesmo padrão é repetido nas demais operações: a interface coleta e apresenta dados; Sistema verifica as regras e mantém as coleções; os objetos do pacote dados representam as informações do domínio.
