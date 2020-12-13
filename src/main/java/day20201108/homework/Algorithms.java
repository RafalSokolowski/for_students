package day20201108.homework;

import java.util.*;

// 1. QuickSort         (+1 pkt)
// 2. CountingSort      (+1 pkt)
// 3. InsertionSort     (+1 pkt)
// 4. BucketSort        (+2 pkt) aka BinSort
// 5. HeapSort          (+2 pkt)
// 6. MergeSort         (+2 pkt)

public class Algorithms {

    public static Fish[] quickSort(Fish[] fish, int startIndex, int endIndex) { // adding start and end index as Im doing it recursively
        if (fish == null) throw new IllegalArgumentException("Table do not exist... thus cannot be sort");
        if (fish.length == 0) throw new IllegalArgumentException("Table is empty... thus cannot be sort");
        if (fish.length == 1) return fish;

        Fish tmpFish;

        if (startIndex < endIndex) {
            Fish pivotFish = fish[endIndex];
            int beforeIndex = (startIndex - 1);

            for (int j = startIndex; j < endIndex; j++) {
                if (fish[j].getWeight() <= pivotFish.getWeight()) {
                    beforeIndex++;

                    tmpFish = fish[beforeIndex];
                    fish[beforeIndex] = fish[j];
                    fish[j] = tmpFish;
                }
            }

            tmpFish = fish[beforeIndex + 1];
            fish[beforeIndex + 1] = fish[endIndex];
            fish[endIndex] = tmpFish;

            quickSort(fish, startIndex, beforeIndex);
            quickSort(fish, beforeIndex + 2, endIndex);
        }

        return fish;
    }

    public static Fish[] countingSort(Fish[] fish, int maxWeight) {
        if (fish == null) throw new IllegalArgumentException("Table do not exist... thus cannot be sort");

        int amountOfFish = fish.length;
        if (amountOfFish == 0) throw new IllegalArgumentException("Table is empty... thus cannot be sort");
        if (amountOfFish == 1) return fish;

        int[] counts = new int[maxWeight + 1];

        for (int i = 0; i < amountOfFish; i++)
            counts[fish[i].getWeight()] += 1;

        for (int i = 1; i < counts.length; i++)
            counts[i] += counts[i - 1];

        Fish[] sorted = new Fish[amountOfFish];
        Fish tmpFish;
        for (int i = amountOfFish - 1; i >= 0; i--) {
            tmpFish = fish[i];
            sorted[counts[tmpFish.getWeight()] - 1] = tmpFish;
            counts[tmpFish.getWeight()] -= 1;
        }
        return sorted;
    }

    public static Fish[] insertionSort(Fish[] fish) {
        if (fish == null) throw new IllegalArgumentException("Table do not exist... thus cannot be sort");

        int amountOfFish = fish.length;
        if (amountOfFish == 0) throw new IllegalArgumentException("Table is empty... thus cannot be sort");
        if (amountOfFish == 1) return fish;

        Fish tmpFish;
        int predecessorIndex;
        for (int i = 1; i < amountOfFish; i++) {
            tmpFish = fish[i];
            predecessorIndex = i - 1;

            while (predecessorIndex >= 0 && fish[predecessorIndex].getWeight() > tmpFish.getWeight()) {
                fish[predecessorIndex + 1] = fish[predecessorIndex];
                predecessorIndex -= 1;
            }
            fish[predecessorIndex + 1] = tmpFish;
        }

        return fish;
    }

    public static Fish[] bucketSort(Fish[] fish, int maxWeight) {
        int arraySize = fish.length;
        Fish[] result = new Fish[fish.length];

        // 1. Buckets' creation
        List<List<Fish>> buckets = new ArrayList<>();
        for (int i = 0; i < maxWeight * arraySize; i++) {
            buckets.add(new ArrayList<Fish>());
        }

        // 2. Put elements into the buckets
        double index;
        for (int i = 0; i < arraySize; i++) {
            index = fish[i].getWeight() * arraySize;
            buckets.get((int) index).add(fish[i]);
        }

        // 3. Sort buckets
        for (int i = 0; i < buckets.size(); i++) {
            buckets.get(i).sort(Comparator.comparingInt(Fish::getWeight));
        }

        // 4. Join buckets
        int resultIndex = 0;
        for (int i = 0; i < maxWeight * arraySize; i++) {
            for (int j = 0; j < buckets.get(i).size(); j++) {
                if (buckets.get(i).get(j) != null) result[resultIndex++] = buckets.get(i).get(j);
            }
        }
        return result;
    }

    public static Fish[] heapSort(Fish[] fish) {
        int arraySize = fish.length;

        // 1. Create heap
        for (int i = arraySize / 2 - 1; i >= 0; i--) {
            fromBinaryTreeToHeap(fish, arraySize, i);
        }

        // 2. Picking fish form the heap
        for (int i = arraySize - 1; i >= 0; i--) {
            Fish tmpFish = fish[0];
            fish[0] = fish[i];
            fish[i] = tmpFish;
            fromBinaryTreeToHeap(fish, i, 0);
        }
        return fish;
    }

    private static void fromBinaryTreeToHeap(Fish[] fish, int heapSize, int nodeIndex) {
        int largestNodeIndex = nodeIndex;
        int leftNodeIndex = 2 * nodeIndex + 1;
        int rightNodeIndex = 2 * nodeIndex + 2;

        if (leftNodeIndex < heapSize && fish[leftNodeIndex].getWeight() > fish[largestNodeIndex].getWeight())
            largestNodeIndex = leftNodeIndex; // ie. left node fish has grater weight than current largest fish weight

        if (rightNodeIndex < heapSize && fish[rightNodeIndex].getWeight() > fish[largestNodeIndex].getWeight())
            largestNodeIndex = rightNodeIndex; // ie. right node fish has grater weight than current largest fish weight

        // If largest fish weight isn't a root... than swap fish
        if (largestNodeIndex != nodeIndex) {
            Fish tmpFish = fish[nodeIndex];
            fish[nodeIndex] = fish[largestNodeIndex];
            fish[largestNodeIndex] = tmpFish;
            fromBinaryTreeToHeap(fish, heapSize, largestNodeIndex);
        }
    }

    public static Fish[] mergeSort(Fish[] fish, int length) { // adding length as Im doing it recursively
        if (length <= 1) return fish;

        int halfIndex = length / 2;
        Fish[] leftFishTable = new Fish[halfIndex];
        Fish[] rightFishTable = new Fish[length - halfIndex];

        for (int i = 0; i < halfIndex; i++) leftFishTable[i] = fish[i];

        for (int i = halfIndex; i < length; i++) rightFishTable[i - halfIndex] = fish[i];

        mergeSort(leftFishTable, halfIndex);
        mergeSort(rightFishTable, length - halfIndex);

        merge(fish, leftFishTable, rightFishTable, halfIndex, length - halfIndex);

        return fish;
    }

    private static void merge(Fish[] fish, Fish[] leftFishTable, Fish[] rightFishTable, int leftIndex, int rightIndex) {

        int baseIndex = 0, nextIndex = 0, fishIndex = 0;

        while (baseIndex < leftIndex && nextIndex < rightIndex) {
            if (leftFishTable[baseIndex].getWeight() <= rightFishTable[nextIndex].getWeight())
                fish[fishIndex++] = leftFishTable[baseIndex++];
            else fish[fishIndex++] = rightFishTable[nextIndex++];
        }

        while (baseIndex < leftIndex) fish[fishIndex++] = leftFishTable[baseIndex++];

        while (nextIndex < rightIndex) fish[fishIndex++] = rightFishTable[nextIndex++];
    }

}
