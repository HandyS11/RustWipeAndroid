package com.iut.clermont.rustwipe.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.iut.clermont.rustwipe.api.data.ServerAPI

const val NEW_SERVER_ID = 0L

@Entity(tableName = "rust_server")
data class Server(
    @PrimaryKey @ColumnInfo(name = "id") val serverId: Long,
    var name: String,
    var type: Gamemode = Gamemode.NONE,
    var rank: Int = 0,

    var country: String? = null,
    var description: String? = null,
    /*var lastWipe: Date? = null,*/

    var ip: String? = null,
    var port: Int? = null,

    var map: Map = Map.OTHER,
    var seed: Long? = null,
    var size: Int? = null,
    var pve: Boolean? = null,
    var thumbnailUrl: String? = null,

    var player: Int? = null,
    var maxPlayer: Int? = null,

    var note: Int = 0,
    )

{
    enum class Map {
        OTHER,
        PROCEDURAL_MAP,
        BARREN,
        CRAGY_ISLAND,
        HAPIS_ISLAND,
        SAVAS_ISLAND_KOTH,
        SAVAS_ISLAND,
        RUST_ISLAND_2013,
        CUSTOM_MAPS
    }

    enum class Gamemode {
        NONE,
        OFFICIAL,
        COMMUNITY,
        MODDED;
    }
}

fun ServerAPI.toModel(): Server {

    return Server(
        serverId = this.id.toLong(),
        name = this.attributes.name,
        type = toGameModeEnum(this.attributes.details.rust_type),
        rank = this.attributes.rank,
        country = this.attributes.country,
        description = this.attributes.details.rust_description,
        ip = this.attributes.ip,
        port = this.attributes.port,
        map = toMapEnum(this.attributes.details.map),
        seed = this.attributes.details.rust_world_seed.toLong(),
        size = this.attributes.details.rust_world_size,
        pve = this.attributes.details.pve,
        thumbnailUrl = this.attributes.details.rust_headerimage,
        player = this.attributes.players,
        maxPlayer = this.attributes.maxPlayers
    )
}

fun toGameModeEnum(string: String): Server.Gamemode {
    return when (string) {
        "official" -> Server.Gamemode.OFFICIAL
        "modded" -> Server.Gamemode.MODDED
        else -> Server.Gamemode.NONE
    }
}

fun toMapEnum(string: String): Server.Map {
    return when (string) {
        "Procedural Map" -> Server.Map.PROCEDURAL_MAP
        else -> Server.Map.OTHER
    }
}