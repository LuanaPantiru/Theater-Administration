CREATE TABLE `sali` (

	`nume` VARCHAR(255) NOT NULL,
	`locuriCategoria1` INTEGER,
    `randuriCategoria1` INTEGER,
    `locuriCategoria2` INTEGER,
    `randuriCategoria2` INTEGER,
    `locuriLoja` INTEGER,
    `randuriLoja` INTEGER,
    `locuriBalcon` INTEGER,
    `randuriBalcon` INTEGER,
	PRIMARY KEY (`nume`)

);
CREATE TABLE `spectacole`(
    `nume` VARCHAR(255) NOT NULL,
    `numeSala` VARCHAR(255) NOT NULL,
    `data` VARCHAR(255) NOT NULL,
    `pretNominal` DOUBLE NOT NULL,
    PRIMARY KEY (`nume`)
);
CREATE TABLE `persoane`(
    `cnp` VARCHAR(255) NOT NULL,
    `nume` VARCHAR(255) NOT NULL,
    `prenume` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`cnp`)
);
CREATE TABLE `rezervate`(
    `numeSpectacol` VARCHAR(255) NOT NULL,
    `cnpPersoana` VARCHAR(255) NOT NULL,
    `categorie` VARCHAR(255) NOT NULL,
    `loc` INTEGER,
    `rand` VARCHAR(255) NOT NULL,
    `pret` DOUBLE
);
