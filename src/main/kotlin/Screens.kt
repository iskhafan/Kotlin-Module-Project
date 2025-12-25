package notes

import java.util.Scanner

object Screens {
    private val scanner = Scanner(System.`in`)

    fun showArchiveList() {
        val menu = Menu(
            title = "Список архивов:",
            items = listOf(
                Pair("Создать архив") { createArchive() },
                Pair("Выбрать архив") { chooseArchive() }
            )
        )
        menu.show()
    }

    private fun createArchive() {
        println("\nСоздание архива:")
        val name = readNonEmptyString("Введите имя архива: ")
        if (DataStore.archives.any { it.name == name }) {
            println("Пояснение: архив с таким именем уже существует.")
            return
        }
        DataStore.archives.add(Archive(name))
        println("Архив \"$name\" создан.")
    }

    private fun chooseArchive() {
        if (DataStore.archives.isEmpty()) {
            println("Нет архивов.")
            return
        }
        val items = DataStore.archives.mapIndexed { idx, arch ->
            Pair("${arch.name}") { showArchive(arch) }
        }
        val menu = Menu(
            title = "Выбор архива:",
            items = items,
            exitText = "Выход"
        )
        menu.show()
    }

    private fun showArchive(archive: Archive) {
        val menu = Menu(
            title = "Архив: ${archive.name}",
            items = listOf(
                Pair("Создать заметку") { createNoteIn(archive) },
                Pair("Выбрать заметку") { chooseNoteFrom(archive) },
            )
        )
        menu.show()
    }

    private fun createNoteIn(archive: Archive) {
        println("\nСоздание заметки:")
        val name = readNonEmptyString("Введите имя заметки: ")
        val content = readNonEmptyString("Введите содержимое заметки: ")
        archive.notes.add(Note(name, content))
        println("Заметка \"$name\" добавлена в архив \"${archive.name}\".")
    }

    private fun chooseNoteFrom(archive: Archive) {
        if (archive.notes.isEmpty()) {
            println("В архиве ${archive.name} нет заметок.")
            return
        }
        val items = archive.notes.mapIndexed { idx, note ->
            Pair("${note.name}") { showNote(note) }
        }
        val menu = Menu(
            title = "Выбор заметки из ${archive.name}",
            items = items,
            exitText = "Выход"
        )
        menu.show()
    }

    private fun showNote(note: Note) {
        println("\nЗаметка: ${note.name}:")
        println(note.content)
        println("\nНажмите любую клавишу, чтобы вернуться")
        scanner.nextLine()
    }

    private fun readNonEmptyString(prompt: String): String {
        while (true) {
            print(prompt)
            val input = scanner.nextLine().trim()
            if (input.isEmpty()) {
                println("Данная строка не может быть пустой.")
            } else {
                return input
            }
        }
    }

}