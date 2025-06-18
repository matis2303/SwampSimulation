# Projekt na zajęcia laboratoryjne z Programowania obiektowego

## Członkowie zespołu

- Mateusz Wikło - lider
- Alina Sidarevich

## Opis projektu
Projekt zakłada symulację lokalnego ekosystemu błota. Głównymi agentami będą żaby, które następnie zostaną podzielone na konkretne gatunki wraz ze swoimi właściwościami oraz modelami zachowania i wzajemnym oddziaływaniem (kanibalizm, nie kanibalizm, sposób polowania, preferencje co do miejsca przesiadywania). 
Projekt zakłada owady jako pożywienie dla części z gatunków żab. Plansza będzie generowana losowo, do wyboru jako parametr będzie możliwość wyboru ilości żab i owadów (wraz z minimalną ilością), czasu trwania symulacji oraz wielkość planszy. Symulacja zakłada również zdarzenia losowe, np. przejście węża przez planszę, co powoduje panikę żab.

## Struktura projektu
```├── main/
      ├── java/
           ├── org/
           │   ├── swampsimulation/
           │       ├── UI/
           │       │     ├── ControlPanel.java
           │       │     ├── MenuFrame.java
           │       │     ├── SimulationBoardPanel.java
           │       │     ├── SimulationConfig.java
           │       │     ├── SimulationEngine.java
           │       │     ├── SwampSimFrame.java
           │       ├── core/
           │       │     ├── Board.java
           │       │     ├── BoardGenerator.java
           │       │     ├── CsvLogger.java
           │       │     ├── Randomizer.java
           │       │     ├── Simulation.java
           │       ├── entities/
           │       │     ├── animal/
           │       │     │      ├── frog/
           │       │     │      │      ├── species/
           │       │     │      │      │     ├── BufoBufoFrog.java
           │       │     │      │      │     ├── FrogSize.java
           │       │     │      │      │     ├── Hide.java
           │       │     │      │      │     ├── PacmanFrog.java
           │       │     │      │      │     ├── TomatoFrog.java
           │       │     │      │      │     ├── TreeFrog.java
           │       │     │      │      ├── Frog.java
           │       │     │      ├── Animal.java
           │       │     │      ├── AnimalSpecies.java
           │       │     │      ├── Fly.java
           │       │     │      ├── Snake.java
           │       │     ├── plants/
           │       │     │        ├── Bushes.java
           │       │     │        ├── Lily.java
           │       │     ├── Plants.java
           │       │     ├── Entity.java
           │       │     ├── Point.java
           │       ├── map/
           │             ├── SwampArea.java
           └── Run.java
```
## Diagramy UML
### Diagram klas

![image](https://github.com/user-attachments/assets/de0a320a-01d9-491d-b289-ceede4d3a0c2)

### Diagram obiektów

![image](https://github.com/user-attachments/assets/8f6d5ce8-4480-4a2d-ae1f-47caaa845ab8)

### Diagramy maszyny stanów

![image](https://github.com/user-attachments/assets/3b2b2cf4-04b3-4b37-969a-9eb4ab0cee73)
![image](https://github.com/user-attachments/assets/1bb657d0-9d67-4128-8f54-0378bde2ec1a)
![image](https://github.com/user-attachments/assets/1e3565da-cc04-4609-a570-60b7e6a81757)

### Diagramy Sekwencji

![image](https://github.com/user-attachments/assets/d088b6b9-d4ce-4a17-858a-c93b75673be0)
![image](https://github.com/user-attachments/assets/9aeb0912-d229-439f-affb-55bdb250262d)







