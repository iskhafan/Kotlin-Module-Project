package notes

import java.util.Scanner

class Menu(
    private val title: String,
    private val items: List<Pair<String, () -> Unit>>,
    private val exitText: String = "Выход"
) {
    private val scanner = Scanner(System.`in`)

    fun show() {
        while (true) {
            println("\n$title")
            items.forEachIndexed { idx, pair -> println("${idx + 1}. ${pair.first}") }
            println("${items.size + 1}. $exitText")

            val choice = readChoice(items.size + 1)
            if (choice == items.size + 1) {
                return
            }
            items[choice - 1].second.invoke()
        }
    }

    private fun readChoice(max: Int): Int {
        // Keep asking input number code until valid value
        while (true) {
            print("Выберите действие: ")
            val line = scanner.nextLine()
            val num = line.toIntOrNull()
            if (num == null) {
                println("Ошибка, введите цифру.")
                continue
            }
            if (num !in 1..max) {
                println("Ошибка, такого пункта нет.")
                continue
            }
            return num
        }
    }
}