alter table TEXTURE drop constraint TEXTURE_GROUP_CODE;
alter table TEXTURE add constraint TEXTURE_CODE_SURFACE_MANF unique(CODE, SURFACE, MANUFACTURER_ID));
