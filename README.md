## Microserviços com Clojure

#### Minha primeira API, requisição HTTP e uso de Kafka em Clojure
Este repositório apresenta um pequeno projeto com o objetivo de colocar alguns conhecimentos em prática. 

Aqui nós temos dois serviços: um é responsável pelo cadastro de um usuário e outro permite o cálculo de Score a fim de identificar se podemos ou não disponibilizar um cartão de crédio para um usuário. Como o principal objetivo deste projeto é **colocar alguns conhecimentos em prática**, teremos algumas decisões de arquitetura que poderiam não se justificar no mundo real.

#### O que eu usei nesse projeto?
- [Datomic](https://www.datomic.com/)
- [Kafka](https://kafka.apache.org/)
- [clj-http](https://github.com/dakrone/clj-http)
- [ring-clojure](https://github.com/ring-clojure/ring)
- [cheshire](https://github.com/dakrone/cheshire)

#### Fluxo
<img width="1102" alt="Screen Shot 2021-12-28 at 15 18 00" src="https://user-images.githubusercontent.com/42384045/147595321-646eb564-36e5-4ffa-aa38-2610dc05a07d.png">

