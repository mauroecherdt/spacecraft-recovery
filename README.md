# 🚀 spacecraft-recovery lib 

Libreria para triangulación de coordenadas e interpretación de mensaje de auxilio en base a los datos recibidos en los satelites Kenobi, Skywalker y Sato.

## Dependencia
**maven:** 
```
<dependency>
    <groupId>ar.com.alianzarebelde</groupId>
    <artifactId>spacecraft-recovery</artifactId>
    <version>1.0.0</version>
</dependency>
```
**gradle:**
```
implementation("ar.com.alianzarebelde:spacecraft-recovery:1.0.0")

```



## Usos

 Método estatico que recibe las distancias al emisor tal cual se recibe en cada satélite. Devuelve un objeto Coordinate con las coordenadas x,y.

```java
public static Coordinate getLocation(float distanceToKenobi, float distanceToSkywalker, float distanceToSato) throws InvalidDistanceException


Ej:
    Coordinate location = LocationResolver.getLocation(400f, 100f, 500f);
```

Método estatico que toma como parametro los 3 mensajes recibidos en los satelites. Devuelve el mensaje interpretado.

```java
public static String getMessage(String[] message1, String[] message2, String[] message3) throws InvalidMessageException


Ej:
    String message = MessageResolver.getMessage(kenobi.getMessage(), skywalker.getMessage(), sato.getMessage());
```


## Consideraciones
- Para el calculo de las coordenadas se plantean 3 ecuaciones que corresponden a la distancia de la ubicación de la nave a cada uno de los satelites. Para la resolución de las mismas se utlizo la regla de Crammer para obtener ls coordenadas x e y en función de las distancias.
```
      4*A - 2*B                         12*B - 8*A  
X = --------------                Y = -----------------
       3200                               3200
       
Donde:

A = d1^2 - d2^2 - 270000          B = 240000 + d2^2 - d3^2

d1: Distancia de la nave al satelite Kenobi.
d2: Distancia de la nave al satelite Skywalker.
d3: Distancia de la nave al satelite Sato.


```

- Una vez obtenidas las coordenadas se verifican contra una de las ecuaciones para corroborar que la cordenada es válida ( se corresponde con las distancias dadas ). 

```
x^2 + y^2 + 1000x + 400y + 290000 == d1^2

Deben ser aproximadamente igual con una tolerancia del 0,5%.
```

- Para la interpretación de los mensajes recibidos si existe al menos un desfasaje en alguno, se procede a alinear los arrays (los otros se rellena con "" al principio). Se aplican las siguientes reglas:
```
- Si en una misma posición todas las palabras son iguales, se aplica la palabra.
- Si en una misma posición todas las palabras son distintas, se aplica "" ( no se puede determinar ).
- Si en una misma posición existen 2 palabras distintas y dos de las mismas son igual a vacio, se aplica la palabra no vacia.
- Si en una misma posición existen 2 palabras distintas y dos de las mismas son distinto de vacio, se aplica la palabra ( mayoria ).

Ej:
Mensaje1: ["este", "zac", "", "", "","mensaje"]
Mensaje2: ["este", "ffdc", "", "", "un",""]
Mensaje3: ["este", "juk", es, "", "un",""]

Resultado: "este es un mensaje"

```


