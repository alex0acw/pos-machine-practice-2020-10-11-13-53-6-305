package pos.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pos.machine.ItemDataLoader.loadAllItemInfos;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        Map<String, Integer> stringIntegerMap =      createItemCountMap(barcodes);
        List<SubtotalDetails> subtotalDetails = new ArrayList<>();
        for (:
             ) {
            
        }

    }

    static List<ItemInfo> itemInfos = loadAllItemInfos();

    //    P: in 2 minute
    //    D: ~2min
    //    C: N/A
    //    A: N/A
    public Map<String, Integer> createItemCountMap(List<String> barcodes) {
        HashMap<String, Integer> barcodeCountMap = new HashMap<>();
        barcodes.forEach(s -> {
            if (barcodeCountMap.containsKey(s)) {
                barcodeCountMap.put(s, barcodeCountMap.get(s) + 1);
            } else
                barcodeCountMap.put(s, 1);
        });
        return barcodeCountMap;
    }

    class SubtotalDetails {
        SubtotalDetails(ItemInfo itemInfo, Float subtotal, Integer quantity) {
            this.itemInfo = itemInfo;
            this.subtotal = subtotal;
            this.quantity = quantity;
        }

        final ItemInfo itemInfo;
        final Float subtotal;
        final Integer quantity;
    }

    public SubtotalDetails getSubtotalDetails(String barcode, Integer itemCount) {
        ItemInfo itemInfo = getItemDetailsByBarcode(barcode);
        return new SubtotalDetails(itemInfo, calculateSubtotal((float) itemInfo.getPrice(), itemCount), itemCount);
    }

    public float calculateSubtotal(Float unitPrice, Integer count) {
        return unitPrice * count;
    }

    ;

    //    P: in 5 minute
    //    D: ~3min
    //    C: N/A
    //    A: N/A
    public ItemInfo getItemDetailsByBarcode(String barcode) {
        for (ItemInfo itemInfo :
                itemInfos) {
            if (itemInfo.getBarcode().equals(barcode))
                return itemInfo;
        }
        return null;
    }

    public String generateReceiptStringFromItems(List<SubtotalDetails> subtotalDetails) {
        Float total = calculateTotalFromSubtotals(subtotalDetails);
        StringBuilder receipt = new StringBuilder();
        for (SubtotalDetails subtotalDetail : subtotalDetails) {
            if (receipt.length() != 0)
                receipt.append("\r\n");
            receipt.append(generatReceiptRow(subtotalDetail));
        }
        receipt.append("----------------------\r\n");
        receipt.append(String.format("Total: %d (yuan)\r\n", total));
        receipt.append("**********************");

        return receipt.toString();
    }

    public String generatReceiptRow(SubtotalDetails subtotalDetails) {
        return String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %f.0 (yuan)",
                subtotalDetails.itemInfo.getName(), subtotalDetails.quantity, subtotalDetails.itemInfo.getPrice(), subtotalDetails.subtotal)
    }


    public Float calculateTotalFromSubtotals(List<SubtotalDetails> subtotalDetails) {
        Float aFloat = (float) 0;
        subtotalDetails.forEach(subtotalDetails1 -> aFloat += subtotalDetails1.subtotal;);
        return aFloat;
    }
}