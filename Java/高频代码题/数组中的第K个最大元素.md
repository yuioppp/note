## 215.数组中的第K个最大元素
给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。

### 优先队列
```java
class Solution {
    public Queue<Integer> priorityQueue = new PriorityQueue();
    public int findKthLargest(int[] nums, int k) {
        for (int num : nums) {
            addNum(num, k);
        }
        return priorityQueue.peek();
    }
    public void addNum(int num, int k) {
        if (priorityQueue.size() < k) {
            priorityQueue.offer(num);
        } else {
            if (priorityQueue.peek() < num) {
                priorityQueue.poll();
                priorityQueue.offer(num);
            }
        }
    }
}
```

### 划分
```java
class Solution {
    public int partion(int[] nums, int start, int end) {
        int pivot = nums[start];
        int i = start, j = end;
        while (i < j) {
            while (i < j && nums[j] <= pivot) {
                j--;
            }
            nums[i] = nums[j];
            while (i < j && nums[i] >= pivot) {
                i++;
            }
            nums[j] = nums[i];
        }
        nums[i]=pivot;
        return i;
    }

    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        int l = 0, r = n - 1;
        while (true) {
            int index = partion(nums, l, r);
            if (index == k - 1)
                return nums[index];
            else if (index < k - 1)
                l = index + 1;
            else
                r = index - 1;
        }
    }
}
```
