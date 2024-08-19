Comentarios y Observaciones

Mensaje del sistema (system): Es importante configurar el mensaje del sistema para guiar al modelo de IA sobre cómo debe responder.

En el código, se utiliza un archivo de recursos para proporcionar este mensaje en algunas de las rutas, mientras que en otras se deja vacío.

Respuestas en flujo: El uso de Flux<String> permite manejar respuestas asíncronas, lo cual es útil para interacciones más complejas o cuando las respuestas pueden ser largas y llegar en partes.

Resultados estructurados: Mapear los resultados a una clase (ResultatSM) permite una manipulación más sencilla y segura de los datos recibidos.

Este código define un conjunto de puntos de entrada REST que interactúan con un servicio de inteligencia artificial utilizando Spring Boot. 
Proporciona la capacidad de manejar flujos de datos asíncronos y de procesar los resultados de manera estructurada o como texto plano, según sea necesario.
