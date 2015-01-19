package grailsstudy

import grails.converters.JSON

/**
 * hasMany, belongsTo, lazy の実験用クラス
 */
class CountryController {

    def get(){
        render Country.get(params.id as Integer) as JSON

        // 初回問い合わせでは、４つのSQLが発行される
        // [1] COUNTRYからのSELECT
        // [2] COUNTRY_STATEからのSELECT
        // [3] STATEからのSELECT 1
        // [4] STATEからのSELECT 2

        // Country -> states cache:true にしていると、
        // 2回目以降の問い合わせでは、[2]を行わない。
        // ただ、States cache:true にしても、Country cache:true にしても、[1][3][4]は必ず実行される！
        // 何か間違っている・・・？
    }

    def getStates(){
        def country = Country.get(params.id as Integer)
        // lazy:false にすると、この時点で４回SELECTを実行する
        // [1] COUNTRYのSELECT
        // [2] COUNTRY_STATEのSELECT -> 2レコード返る
        // [3] STATEのSELECT 1
        // [4] STATEのSELECT 2

        // lazy:true　にすると、この時点では、[1]だけ実行

        println country.states
        // lazy:true　にすると、この時点ではじめて、[2][3][4]を実行する
        // lazy:false にすると、もうデータは取得済なので、SQLは何も実行しない

        render states as JSON
    }
}
