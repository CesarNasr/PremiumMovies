package com.example.premiummovies.data.mapper

interface EntityMapper <Entity, DomainModel>{

    fun mapFromLocalEntity(entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity

}


interface DtoMapper <DTO, DomainModel>{

    fun mapFromDto(dto: DTO): DomainModel

    fun mapToDto(domainModel: DomainModel): DTO

}