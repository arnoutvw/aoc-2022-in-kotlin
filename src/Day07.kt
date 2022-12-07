import kotlin.collections.ArrayList

fun main() {

    data class Dir(val prevDir: Dir?, val subdirs : ArrayList<Dir>, var size: Int, val name: String) {
        fun calculateSize() :Int {
            return subdirs.sumOf { it.calculateSize() } + size
        }

        fun getSubDir(name: String) :Dir? {
            return subdirs.firstOrNull { it.name == name }
        }

        override fun toString(): String {
            return "Dir(subdirs=$subdirs, size=$size, name='$name')"
        }

    }

    fun checkSmallDir(dir: Dir, size: Int): Int {
        var sizeSmallDirs = size
        if (dir.calculateSize() <= 100000) {
            sizeSmallDirs += dir.calculateSize();
        }
        for(subDir in dir.subdirs) {
            sizeSmallDirs += checkSmallDir(subDir, 0)
        }
        return sizeSmallDirs;
    }

    fun populateDirs(input: List<String>, currentDir: Dir) {
        var currentDir1 = currentDir
        input.forEach {
            if (it == "$ cd /" || it.startsWith("$ ls")) {
                //SKIP
            } else if (it.startsWith("$ cd")) {
                val dirname = it.substring(5)
                if (dirname == "..") {
                    currentDir1 = currentDir1.prevDir!!
                } else {
                    var subDir = currentDir1.getSubDir(dirname)
                    if (subDir == null) {
                        subDir = Dir(currentDir1, ArrayList(), 0, dirname)
                        currentDir1.subdirs.add(subDir)
                    }
                    currentDir1 = subDir
                }
            } else {
                if (it.startsWith("dir")) {
                    currentDir1.subdirs.add(Dir(currentDir1, ArrayList(), 0, it.substring(4)))
                } else {
                    val split = it.split(" ")
                    currentDir1.size += split.get(0).toInt()
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val currentDir = Dir(null, ArrayList<Dir>(), 0, "/")
        populateDirs(input, currentDir)
        return checkSmallDir(currentDir, 0)
    }

    fun addDirsToList(dirs: ArrayList<Dir>, startDir: Dir) {
        dirs.add(startDir)
        for(subDir in startDir.subdirs) {
            addDirsToList(dirs, subDir)
        }
    }

    fun part2(input: List<String>): Int {
        val currentDir = Dir(null, ArrayList<Dir>(), 0, "/")
        populateDirs(input, currentDir)
        val minFreeup = 30000000 - (70000000 - currentDir.calculateSize())

        val dirs = ArrayList<Dir>()
        addDirsToList(dirs, currentDir)
        return dirs.filter { it.calculateSize() >= minFreeup }.minByOrNull { it.calculateSize() }!!.calculateSize()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    println("Test")
    println(part1(testInput))
    println(part2(testInput))
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    println("Waarde")
    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
