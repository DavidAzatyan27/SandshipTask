import com.sandship.models.material.Bolt;
import com.sandship.models.material.Cooper;
import com.sandship.models.material.Iron;
import com.sandship.models.material.Material;
import com.sandship.models.player.Player;
import com.sandship.models.warehouse.Warehouse;
import com.sandship.observer.WarehouseManager;

public class WarehouseClient {
    public static void main(String[] args) {

        //Create data
        Material iron = new Iron(1200);
        Material cooper = new Cooper(1500);
        Material bolt = new Bolt(800);

        //Create player
        Player player = new Player(1, "Player1");

        //Create first Warehouse for player and add materials to Warehouse
        Warehouse firstWarehouse = new Warehouse(player.getId(), "First Warehouse");
        firstWarehouse.addMaterial(iron, 1100);
        firstWarehouse.addMaterial(cooper, 1300);

        //Create second Warehouse  for player and add materials to Warehouse
        Warehouse secondWarehouse = new Warehouse(player.getId(), "Second Warehouse");
        secondWarehouse.addMaterial(bolt, 600);


        player.getWarehouses().add(firstWarehouse);
        player.getWarehouses().add(secondWarehouse);

        //print warehouses current data
        firstWarehouse.printWarehouseData();
        secondWarehouse.printWarehouseData();

        //Create observers to monitor warehouse object
        WarehouseManager firstObserver = new WarehouseManager("First observer");
        WarehouseManager secondObserver = new WarehouseManager("Second observer");

        firstWarehouse.addObserver(firstObserver);
        secondWarehouse.addObserver(secondObserver);

        //move materials between warehouses and remove material
        firstWarehouse.moveMaterial(secondWarehouse, iron, 500);
        firstWarehouse.moveMaterial(secondWarehouse, cooper, 700);
        secondWarehouse.moveMaterial(firstWarehouse, bolt, 300);
        secondWarehouse.removeMaterial(bolt, 50);

        //print warehouses current data
        firstWarehouse.printWarehouseData();
        secondWarehouse.printWarehouseData();

    }
}
