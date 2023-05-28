package com.iut.clermont.rustwipe.api.data

data class ServerAPI(
    val type: String,
    val id: String,
    val attributes: Attributes,
    val relationships: Relationships
)

data class Attributes(
    val name: String,
    val ip: String,
    val port: Int,
    val players: Int,
    val maxPlayers: Int,
    val rank: Int,
    val details: Details,
    val country: String
)

data class Details(
    val rust_gc_mb: Int?,
    val rust_build: String,
    val official: Boolean,
    val rust_gamemode: String,
    val rust_mem_pv: Int?,
    val rust_headerimage: String,
    val rust_world_seed: Int,
    val rust_last_wipe: String,
    val pve: Boolean,
    val rust_fps_avg: Double,
    val rust_type: String,
    val rust_world_size: Int,
    val rust_description: String,
    val rust_last_seed_change: String,
    val rust_last_wipe_ent: Int,
    val rust_last_ent_drop: String,
    val rust_hash: String,
    val rust_uptime: Int,
    val rust_maps: RustMaps,
    val rust_born: String,
    val rust_map_size_ent_count: String,
    val rust_modded: Boolean,
    val rust_mem_ws: Int?,
    val rust_ent_cnt_i: Int,
    val rust_gc_cl: Int,
    val rust_queued_players: Int,
    val environment: String,
    val rust_url: String,
    val rust_fps: Int,
    val map: String,
    val serverSteamId: String
)

data class RustMaps(
    val thumbnailUrl: String,
    val seed: Int,
    val size: Int,
    val monuments: Int,
    val url: String,
    val barren: Boolean
)

data class Relationships(
    val game: Game
)

data class Game(
    val data: GameData
)

data class GameData(
    val type: String,
    val id: String
)

data class Data(
    val data: List<ServerAPI>,
    val links: Links
)

data class Links(
    val next: String
)