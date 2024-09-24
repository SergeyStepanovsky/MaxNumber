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
        // Запускаем таймер
        long startTime = System.currentTimeMillis();

        // Чтение чисел из Excel файла по указанному пути
        List<Integer> numbers = readNumbersFromExcel(filePath);

        // Проверка, что число N находится в допустимом диапазоне
        if (n < 1 || n > numbers.size()) {
            throw new IllegalArgumentException("Неверное значение N");
        }

        // Вызов метода quickSelect для поиска N-го максимального числа
        Integer result = quickSelect(numbers, 0, numbers.size() - 1, numbers.size() - n);

        // Останавливаем таймер
        long endTime = System.currentTimeMillis();

        // Выводим время выполнения в миллисекундах
        System.out.println("Время выполнения программы: " + (endTime - startTime) + " мс");

        // Возвращаем результат
        return result;
    }

    // Метод для чтения чисел из Excel файла
    private List<Integer> readNumbersFromExcel(String filePath) throws Exception {
        List<Integer> numbers = new ArrayList<>();

        // Открываем Excel файл
        Workbook workbook = WorkbookFactory.create(new File(filePath));

        // Получаем первый лист из файла
        Sheet sheet = workbook.getSheetAt(0);

        // Переменная для хранения номера первого столбца с данными
        int dataColumnIndex = -1;

        // Проходим по каждой строке листа
        for (Row row : sheet) {
            // Если столбец с данными уже найден
            if (dataColumnIndex != -1) {
                // Читаем только из этого столбца
                Cell cell = row.getCell(dataColumnIndex);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    numbers.add((int) cell.getNumericCellValue());
                }
            } else {
                // Ищем первый столбец с данными
                for (Cell cell : row) {
                    if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                        // Запоминаем индекс столбца с данными
                        dataColumnIndex = cell.getColumnIndex();
                        // Добавляем первое число в список
                        numbers.add((int) cell.getNumericCellValue());
                        break; // Прекращаем искать другие столбцы
                    }
                }
            }
        }

        // Закрываем Excel файл для освобождения ресурсов
        workbook.close();

        // Возвращаем список чисел
        return numbers;
    }

    // Реализация алгоритма QuickSelect для поиска N-го максимального числа
    private int quickSelect(List<Integer> nums, int low, int high, int k) {
        // Базовый случай: если в диапазоне только один элемент, возвращаем его
        if (low == high) {
            return nums.get(low);
        }

        // Разделяем список на две части и получаем индекс опорного элемента
        int pivotIndex = partition(nums, low, high);

        // Если индекс опорного элемента совпадает с искомым k, возвращаем его
        if (k == pivotIndex) {
            return nums.get(k);
        }
        // Если k меньше, продолжаем поиск в левой части массива
        else if (k < pivotIndex) {
            return quickSelect(nums, low, pivotIndex - 1, k);
        }
        // Если k больше, продолжаем поиск в правой части массива
        else {
            return quickSelect(nums, pivotIndex + 1, high, k);
        }
    }

    // Метод для разделения массива вокруг опорного элемента (pivot)
    private int partition(List<Integer> nums, int low, int high) {
        // Выбираем последний элемент как опорный (pivot)
        int pivot = nums.get(high);
        int i = low;

        // Переставляем элементы так, чтобы меньшие или равные pivot были слева
        for (int j = low; j < high; j++) {
            if (nums.get(j) <= pivot) {
                // Меняем местами элементы nums[i] и nums[j]
                swap(nums, i, j);
                i++;
            }
        }

        // Ставим опорный элемент на его правильное место
        swap(nums, i, high);

        // Возвращаем индекс опорного элемента
        return i;
    }

    // Метод для обмена местами двух элементов в списке
    private void swap(List<Integer> nums, int i, int j) {
        int temp = nums.get(i); // Временная переменная для хранения значения
        nums.set(i, nums.get(j)); // Устанавливаем значение nums[j] в позицию i
        nums.set(j, temp); // Устанавливаем сохраненное значение temp в позицию j
    }
}
