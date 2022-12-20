fun main() {
    val input = getInput("day20").readLines().map { it.toLong() }
    mix(input)
    mix(input, 10) { it * 811589153L }
}

private fun mix(input: List<Long>, numberOfMixings: Int = 1, init: (Long) -> Long = { x -> x}) {
    val els = input.map { DoublyLinkedListElement(init(it)) }
    val zero = els.find { it.value == 0L }
    val size = input.size

    (listOf(els.last()) + els + els.first()).windowed(3)
        .forEach { (prev, curr, next) ->
            curr.prev = prev; curr.next = next
        }

    repeat(numberOfMixings) { els.forEach {
        val t = ((it.value + size - 1) % (size - 1)).toInt()
        val steps = if (t > (size - 1) / 2) -size + 1 + t else t
        it.move(steps)
    }}

    val list = (0 until size - 1).fold(listOf(zero)) { l, _ -> l + l.last()?.next }
    println(list[1000 % size]!!.value + list[2000 % size]!!.value + list[3000 % size]!!.value)
}

data class DoublyLinkedListElement <T> (val value: T) {
    var prev: DoublyLinkedListElement<T> = this
    var next: DoublyLinkedListElement<T> = this

    fun move(steps: Int) {
        var x = steps
        var p = prev
        var n = next
        n.prev = p
        p.next = n
        while (x < 0){
            n = p
            p = p.prev
            x++
        }
        while (x > 0){
            p = n
            n = n.next
            x--
        }
        p.next = this
        n.prev = this
        this.prev = p
        this.next = n
    }

    override fun toString(): String {
        return "$value"
    }
}