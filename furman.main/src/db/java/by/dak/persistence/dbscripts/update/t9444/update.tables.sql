update furniture_type ft set ft.offset_lenght=0 where ft.discriminator = 'BoardDef';
update furniture_type ft set ft.offset_width=0 where ft.discriminator = 'BoardDef';

