package com.example.premiummovies.data.mapper

interface EntityMapper <Entity, DomainModel>{


}


interface Mapper <DTO, DomainModel, Entity>{

    fun mapFromDto(dto: DTO): DomainModel

    fun mapToDto(domainModel: DomainModel): DTO

    fun mapFromLocalEntity(entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity

}