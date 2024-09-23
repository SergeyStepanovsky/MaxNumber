package com.example.demo.service;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class NumberServiceImpl implements NumberService {

    @Override
    public Integer findMaxNumber(String filePath, int n) throws Exception {
        List<Integer> numbers = readNumbersFromExcel(filePath);
        if (n < 1 || n > numbers.size()) {
            throw new IllegalArgumentException("Неверное значение N");
        }
        return quickSelect(numbers, 0, numbers.size() - 1, numbers.size() - n);
    }

    private List<Integer> readNumbersFromExcel(String filePath) throws Exception {
        List<Integer> numbers = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(new File(filePath));
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                numbers.add((int) cell.getNumericCellValue());
            }
        }
        workbook.close();
        return numbers;
    }

    private int quickSelect(List<Integer> nums, int low, int high, int k) {
        if (low == high) {
            return nums.get(low);
        }
        int pivotIndex = partition(nums, low, high);
        if (k == pivotIndex) {
            return nums.get(k);
        } else if (k < pivotIndex) {
            return quickSelect(nums, low, pivotIndex - 1, k);
        } else {
            return quickSelect(nums, pivotIndex + 1, high, k);
        }
    }

    private int partition(List<Integer> nums, int low, int high) {
        int pivot = nums.get(high);
        int i = low;
        for (int j = low; j < high; j++) {
            if (nums.get(j) <= pivot) {
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, i, high);
        return i;
    }

    private void swap(List<Integer> nums, int i, int j) {
        int temp = nums.get(i);
        nums.set(i, nums.get(j));
        nums.set(j, temp);
    }
}