package grailsstudy

/**
 * lazy, cache の挙動調査
 */
class Country {

    String name

    static hasMany = [states:State]

    static mapping = {
        states lazy:true, cache:true // 二次キャッシュ（read-writeキャッシュ）を有効にする
        cache:true
    }

    // hibernateキャッシュについて
    // [1次キャッシュ]
    // Sessionスコープで有効
    // [2次キャッシュ]
    // サーバのプロセス内で有効。1次キャッシュ（＝セッション）が切れても、キャッシュされるということ。
    // 更新頻度が低い、参照用のオブジェクトに適している

    // see also:
    // http://grails.jp/doc/2.3.x/guide/GORM.html#fetching
    // http://grails.jp/doc/2.3.x/guide/single.html

}
