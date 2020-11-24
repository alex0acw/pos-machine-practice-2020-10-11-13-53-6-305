package pos.machine;

import java.util.*;

import static pos.machine.ItemDataLoader.loadAllItemInfos;

public class PosMachine {
    static final String NEW_LINE = String.format("%n");

    //    P: in 2 minute
    //    D: ~3min
    //    C: test case is sorted manually by barcode, had to change implementation to use SortedMap instead of Map
    //    A: N/A
    public String printReceipt(List<String> barcodes) {
        SortedMap<String, Integer> stringIntegerMap = createItemCountMap(barcodes);
        List<SubtotalDetails> subtotalDetails = new ArrayList<>();
        stringIntegerMap.forEach((barcode, count) -> subtotalDetails.add(
                getSubtotalDetails(barcode, count)
        ));
        return generateReceiptStringFromItems(subtotalDetails);

    }

    static List<ItemInfo> itemInfos = loadAllItemInfos();

    //    P: in 2 minute
    //    D: ~2min
    //    C: N/A
    //    A: N/A
    public SortedMap<String, Integer> createItemCountMap(List<String> barcodes) {
        SortedMap<String, Integer> barcodeCountMap = new TreeMap<>();
        barcodes.forEach(s -> {
            if (barcodeCountMap.containsKey(s)) {
                barcodeCountMap.put(s, barcodeCountMap.get(s) + 1);
            } else
                barcodeCountMap.put(s, 1);
        });
        return barcodeCountMap;
    }

    static class SubtotalDetails {
        SubtotalDetails(ItemInfo itemInfo, Float subtotal, Integer quantity) {
            this.itemInfo = itemInfo;
            this.subtotal = subtotal;
            this.quantity = quantity;
        }

        final ItemInfo itemInfo;
        final Float subtotal;
        final Integer quantity;
    }

    //    P: <1min
    //    D: <1min
    //    C: N/A
    //    A: N/A
    public SubtotalDetails getSubtotalDetails(String barcode, Integer itemCount) {
        ItemInfo itemInfo = getItemDetailsByBarcode(barcode);
        return new SubtotalDetails(itemInfo, calculateSubtotal((float) itemInfo.getPrice(), itemCount), itemCount);
    }

    //    P: <1min
    //    D: <1min
    //    C: N/A
    //    A: N/A
    public float calculateSubtotal(Float unitPrice, Integer count) {
        return unitPrice * count;
    }

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

    //    P: in 3 minute
    //    D: ~5min
    //    C: newline difference should be OS depended, had to make a constant
    //    A: N/A
    public String generateReceiptStringFromItems(List<SubtotalDetails> subtotalDetails) {
        Float total = calculateTotalFromSubtotals(subtotalDetails);
        StringBuilder receipt = new StringBuilder();
        receipt.append("***<store earning no money>Receipt***").append(NEW_LINE);
        for (SubtotalDetails subtotalDetail : subtotalDetails) {
            receipt.append(generateReceiptRow(subtotalDetail));
            receipt.append(NEW_LINE);
        }
        receipt.append("----------------------").append(NEW_LINE);
        receipt.append(String.format("Total: %d (yuan)", (int) total.floatValue())).append(NEW_LINE);
        receipt.append("**********************");

        return receipt.toString();
    }

    //    P: <2min
    //    D: ~1min
    //    C: N/A
    //    A: N/A
    public String generateReceiptRow(SubtotalDetails subtotalDetails) {
        return String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)",
                subtotalDetails.itemInfo.getName(), subtotalDetails.quantity, subtotalDetails.itemInfo.getPrice(), (int) subtotalDetails.subtotal.floatValue());
    }


    //    P: <1min
    //    D: <1min
    //    C: N/A
    //    A: N/A
    public Float calculateTotalFromSubtotals(List<SubtotalDetails> subtotalDetails) {
        Float aFloat = (float) 0;
        for (SubtotalDetails subtotalDetails1 : subtotalDetails
        )
            aFloat += subtotalDetails1.subtotal;

        return aFloat;
    }
}