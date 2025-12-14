<?php
// Datos de conexión a la base de datos 
$servername = "193.203.168.33";
$username = "";
$password = "";
$dbname = "u339278717_revi";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

function error($str)
{
  header("Content-Type: application/json");
  echo json_encode(array(
    "error" => $str ? $str : "Generic error",
    "success" => false
  ));
  exit();
}

// Verificar conexión
if ($conn->connect_error) {
  die();
} else {
  header('Access-Control-Allow-Origin: *');
  header("Access-Control-Allow-Headers: X-API-KEY, Origin, X-Requested-With, Content-Type, Accept, Access-Control-Request-Method");
  header("Access-Control-Allow-Methods: GET, POST, OPTIONS, PUT, DELETE");
  header("Allow: GET, POST, OPTIONS, PUT, DELETE");
  $method = $_SERVER['REQUEST_METHOD'];
  if($method == "OPTIONS") {
      die();
  }

  switch ($_GET['q']) {
    case 'machine':
      if (!$_GET["serial"])
        error("Falta el número de serie");
      
      $sql = "SELECT * FROM machines WHERE serialNumber='". $_GET["serial"] ."'";
      $result = $conn->query($sql);

      $machines = array();

      if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
          error_log(print_r("FILA".$row, TRUE));
          array_push(
            $machines,
            array(
              "id" => $row["id"],
              "model" => $row["model"],
              "engine" => $row["engine"],
              "weight" => $row["weight"],
              "dateOfPurchase" => $row["dateOfPurchase"],
              "guarantee" => $row["guarantee"],
              "hours" => $row["hours"],
              "serialNumber" => $row["serialNumber"],
              "extras" => $row["extras"] != "" ? explode(",", $row["extras"]) : []
            )
          );
        }
      }

      header("Content-Type: application/json");
      echo json_encode($machines[0]);
      exit();

    case 'maintenances':
      if (!$_GET["serial"])
        error("Falta el número de serie");

      $sql = "SELECT * FROM machines WHERE serialNumber='". $_GET["serial"];
      $result = $conn->query($sql);

      $machines = array();

      if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
          array_push(
            $machines,
            array(
              "id" => $row["id"],
              "model" => $row["model"],
              "engine" => $row["engine"],
              "weight" => $row["weight"],
              "dateOfPurchase" => $row["dateOfPurchase"],
              "guarantee" => $row["guarantee"],
              "hours" => $row["hours"],
              "serialNumber" => $row["serialNumber"],
              "extras" => $row["extras"] != "" ? explode(",", $row["extras"]) : []
            )
          );
        }
      }

      header("Content-Type: application/json");
      echo json_encode($machines[0]);
      exit();
  }
}

// Cerrar conexión (opcional, ya que PHP cierra automáticamente las conexiones)
$conn->close();
?>