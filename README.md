
## API Routes

### CartController

Entidad `Cart`, simula un carrito de compra y contiene un listado de ordenes de compra (`Order`). Un carrito es eliminado por el servicio `CartDumpService` al transcurrir `10 min` des de su última actualización.

| Método | Dirección           | Descripción                                                                                             |
|--------|---------------------|---------------------------------------------------------------------------------------------------------|
| GET    | /api/data/carts     | Devuelve una lista de carritos.                                                                         |
| GET    | /api/data/cart/{id} | Devuelve un carrito a partir de su `id` pasado por parámetro.                                           |
| POST   | /api/data/cart      | Crea un carrito nuevo.                                                                                  |
| DELETE | /api/data/carts     | Elimina todos los carritos y sus ordenes de compra asociadas.                                           |    
| DELETE | /api/data/cart/{id} | Elimina un carrito a partir de su `id` pasado por parámetro, junto con sus ordenes de compra asociadas. |

### ProductController

Entidad `Product`, simula un producto y puede ser asociado a múltiples ordenes de compra (`Order`).

| Método | Dirección              | Descripción                                                                                                                 |
|--------|------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| GET    | /api/data/products     | Devuelve una lista de productos.                                                                                            |
| GET    | /api/data/product/{id} | Devuelve un producto a partir de su `id` pasado por parámetro.                                                              |
| POST   | /api/data/product      | Crea o actualiza un producto con los datos mandados por *request* con formato JSON: `{"name": "...", "description": "..."}` |
| DELETE | /api/data/products     | Elimina todos los productos y sus ordenes de compra asociadas.                                                              |    
| DELETE | /api/data/product/{id} | Elimina un producto a partir de su `id` pasado por parámetro, junto con sus ordenes de compra asociadas.                    |

### OrderController

Entidad `Order`, simula una orden de compra, contiene una referencia al carrito al que pertenece (`cartId`) y al producto al que hace referència (`productId`), junto con la cantidad de unidades del producto referenciado para esa orden.

| Método | Dirección                            | Descripción                                                                                                                                                              |
|--------|--------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| GET    | /api/data/orders                     | Devuelve una lista de ordenes de compra.                                                                                                                                 |
| GET    | /api/data/order/{cartId}/{productId} | Devuelve una orden de compra a partir de los identificadores de su carrito (`cartId`) y producto (`productId`).                                                          |
| POST   | /api/data/order                      | Crea o actualiza una orden de compra con los datos mandados por *request* con formato JSON: `{"cartId": <cartId>, "productId": <productId>, "amount": <integer>}`. |
| DELETE | /api/data/orders                     | Elimina todas las ordenes de compra.                                                                                                                                     |    
| DELETE | /api/data/order/{cartId}/{productId} | Elimina una orden de compra a partir de los identificadores de su carrito (`cartId`) y producto (`productId`).                                                           |

# Instal·lación y arranque del proyecto

Realizar build del proyecto con los prametros del Dockerfile y arrancar las imagenes de Docker configuradas en el archivo `docker-compose.yaml`.

```
project_dir> docker-compose build --no-cache

project_dir> docker-compose up
```

# Tests

Para realizar los tests unitarios

```
project_dir> ./mvnw test
```
