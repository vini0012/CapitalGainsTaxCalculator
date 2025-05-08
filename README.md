# Capital Gains Tax Calculator

## Descrição do Projeto

Esta aplicação é uma calculadora de ganho de capital que processa operações de compra e venda de ações e calcula os impostos devidos com base nas regras de isenção e dedução de prejuízos acumulados. A solução foi projetada para receber uma lista de operações em formato JSON e retornar uma lista de objetos com o imposto correspondente a cada operação.

## Decisões Técnicas e Arquiteturais

### 1. Arquitetura Baseada em Estratégias
Para lidar com a lógica distinta entre operações de compra e venda, optei pelo uso do padrão **Strategy**. Esse padrão permite definir diferentes estratégias de cálculo de impostos para compras (`BuyStrategy`) e vendas (`SellStrategy`). Isso torna o código modular e facilita a manutenção e extensibilidade.

### 2. Desacoplamento de Lógica com o Padrão Factory
A lógica de vendas pode envolver diversas verificações, como isenção e dedução de prejuízos. Para gerenciar essas condições, utilizei uma **Factory** (`OperationHandlerFactory`) que cria handlers específicos para essas condições. A `SellStrategy` então delega para esses handlers (`ExemptHandler`, `LossHandler`) a execução de regras específicas, mantendo sua lógica principal mais limpa e organizada.

### 3. Organização por Pacotes
O projeto foi estruturado em pacotes para facilitar a organização e a escalabilidade:
- **model**: Contém as classes de dados, como `Operation` e `TaxResult`.
- **enums**: Contém a enumeração `OperationType` para representar os tipos de operação.
- **service**: Contém as estratégias de cálculo e a lógica de contexto (`TaxCalculationContext`).
- **service.strategy**: Contém as implementações das estratégias de cálculo de imposto.
- **controller**: Contém a classe `CapitalGainsCalculatorController`, que gerencia a interação com o JSON de entrada.
- **utils**: Contém utilitários, como `BigDecimalUtils` para operações de arredondamento e formatação.
- **serialization**: Inclui o deserializador personalizado (`OperationDeserializer`) para configurar como os dados JSON são mapeados para os objetos Java.

### 4. Testes Unitários
Os testes foram implementados para cobrir diversos cenários de operações de compra e venda, incluindo casos complexos com múltiplas transações e diferentes cenários de isenção e lucro. Cada teste verifica o cálculo de imposto esperado para um conjunto de operações específico, o que ajuda a validar o comportamento da aplicação em diversas situações.

## Justificativa para o Uso de Frameworks/Bibliotecas

Este projeto não faz uso de frameworks, ele foi feito totalmente em Java puro na versão 21. Contudo, utilizei a biblioteca **Jackson** para manipulação de JSON, pois é uma das mais robustas e eficientes para esse tipo de tarefa em Java. Jackson permite deserializar e serializar dados JSON facilmente, o que simplifica a entrada e saída de dados na aplicação, reduzindo o potencial para erros e tornando o código mais limpo.

## Padrões de Projeto Utilizados

- **Strategy**: Encapsula diferentes lógicas de cálculo de imposto, tornando o código modular e fácil de estender.
- **Factory**: `OperationHandlerFactory` é usada para criar handlers específicos de `SellStrategy`, facilitando a organização e encapsulamento da lógica de isenção e acúmulo de prejuízos.
- **Template Method**: Utilizado nos handlers (`ExemptHandler`, `LossHandler`) para padronizar a lógica de `handle`, simplificando e reutilizando o código.

## Instruções para Compilação e Execução do Projeto

### Compilar o Projeto

1. Baixe os arquivos .jar necessários da biblioteca Jackson. Você pode encontrar as versões utilizadas no projeto em: [Jackson Core](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core/2.18.0), [Jackson Databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/2.18.0), [Jackson Annotations](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations/2.18.0).
2. Coloque os arquivos .jar baixados em um diretório, por exemplo, libs/
3. Navegue até o diretório raiz do projeto.
4. Execute o seguinte comando para compilar o código, incluindo o caminho dos arquivos .jar baixados:
   ```bash
   javac -d bin -sourcepath src -cp "libs/*" src/CapitalGainsCalculatorApplication.java

Isso compilará todos os arquivos Java e armazenará os arquivos .class no diretório bin.

### Executar o Projeto
1. Depois de compilar, você pode executar o projeto com o seguinte comando:
    ```bash
    java -cp "bin:libs/*" CapitalGainsCalculatorApplication

2. Caso possua uma IDE de desenvolvimento como por exemplo IntelliJ IDEA, ou Eclipse, basta importar o projeto e executar a classe `CapitalGainsCalculatorApplication`. No entanto, certifique-se de estar com o JDK 21 configurado na IDE e também com as dependências importadas corretamente no classpath. Sendo elas:
    - [Jackson Core v2.18.0](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/2.18.0)
    - [Jackson Databind v2.18.0](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/2.18.0)
    - [Jackson Annotations v2.18.0](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations/2.18.0)

**Muito Importante:** Certifique-se de estar com o Java 21 instalado e configurado corretamente em seu ambiente. Você pode verificar a versão do Java com o comando `java -version`. As libs do Jackson não estão incluídas no projeto devido a instrução do teste que dizia para não enviar bibliotecas no arquivo comprimido (zip), portanto, é necessário baixá-las manualmente.

## Instruções para Executar os Testes
Como os testes foram implementados sem frameworks de teste, eles podem ser executados diretamente no terminal. 

### Compilar os Testes
1. Baixe os arquivos .jar necessários da biblioteca Jackson. Você pode encontrar as versões utilizadas no projeto em: [Jackson Core](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core/2.18.0), [Jackson Databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/2.18.0), [Jackson Annotations](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations/2.18.0).
2. Coloque os arquivos .jar baixados em um diretório, por exemplo, libs/
3. Navegue até o diretório raiz do projeto.
4. Execute o seguinte comando para compilar o código, incluindo o caminho dos arquivos .jar baixados:
   ```bash
   javac -d bin -sourcepath src -cp "libs/*" src/CapitalGainsCalculatorApplicationTest.java

### Executar os Testes
1. Depois de compilar, você pode executar os testes do projeto com o seguinte comando:
    ```bash
    java -cp "bin:libs/*" CapitalGainsCalculatorApplicationTest
2. Caso possua uma IDE de desenvolvimento como por exemplo IntelliJ IDEA, ou Eclipse, basta importar o projeto e executar a classe `CapitalGainsCalculatorApplicationTest`. No entanto, certifique-se de estar com o JDK 21 configurado na IDE e também com as dependências importadas corretamente no classpath. Sendo elas:
   - [Jackson Core v2.18.0](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/2.18.0)
   - [Jackson Databind v2.18.0](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/2.18.0)
   - [Jackson Annotations v2.18.0](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations/2.18.0)

Os testes cobrem uma variedade de cenários e verificarão se os cálculos de imposto estão corretos. Cada teste imprimirá "Unexpected result" no caso de uma falha, junto com o resultado real para comparação.

## Notas Adicionais
- **Formato de Entrada:** A aplicação foi desenvolvida para receber múltiplas listas de operações em formato JSON. Cada lista é processada de forma independente.
- **Arredondamento:** Utilizei BigDecimal para garantir precisão nos cálculos de valores monetários, com arredondamento para duas casas decimais através do método BigDecimalUtils.roundToTwoDecimalPlaces.
- **Tratamento de Exceções:** A aplicação lida com possíveis exceções de deserialização JSON e erros de cálculo para garantir que falhas sejam registradas e não interrompam a execução da aplicação.

## Exemplo de Uso
### Exemplo de Entrada

```json
[{"operation":"buy","unit-cost":10.00,"quantity":100},{"operation":"sell","unit-cost":15.00,"quantity":50},{"operation":"sell","unit-cost":15.00,"quantity":50}] [{"operation":"buy","unit-cost":10.00,"quantity":10000},{"operation":"sell","unit-cost":20.00,"quantity":5000},{"operation":"sell","unit-cost":5.00,"quantity":5000}]
```

### Exemplo de Saída
    
```json
[{"tax":0},{"tax":0},{"tax":0}]
[{"tax":0},{"tax":10000.00},{"tax":0}]
```

### Importante
- Quando a entrada for composta por duas listas de operações, estas devem ser separadas por um espaço e não por quebra de linhas. Cada lista é considerado um array JSON contendo objetos de operação.

## Estrutura de Pastas
A estrutura de pastas do projeto é organizada da seguinte forma:
```
CapitalGainsTaxCalculator
├── README.md                        
├── src
│   ├── controller
│   │   └── CapitalGainsCalculatorController.java
│   ├── enums
│   │   └── OperationType.java
│   ├── model
│   │   ├── Operation.java
│   │   └── TaxResult.java
│   ├── serialization
│   │   └── OperationDeserializer.java
│   ├── service
│   │   ├── CapitalGainsCalculatorService.java
│   │   ├── TaxCalculationContext.java
│   │   └── strategy
│   │       ├── BuyStrategy.java
│   │       ├── OperationHandlerFactory.java
│   │       ├── SellStrategy.java
│   │       ├── TaxCalculationStrategy.java
│   ├── utils
│   │   └── BigDecimalUtils.java
├── CapitalGainsCalculatorApplication.java
└── CapitalGainsCalculatorApplicationTest.java
```