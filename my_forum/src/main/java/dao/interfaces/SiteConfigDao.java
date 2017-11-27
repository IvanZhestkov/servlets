package dao.interfaces;

import pojo.Site;

/**
 * Интерфейс, описывающий методы для работы
 * с конфигурацией сайта
 */
public interface SiteConfigDao {

    /*Создание конфига сайта*/
    void createConfig(Site site);

    /*Обновление конфига сайта*/
    void updateConfig(Site site);

    /*Получение конфига сайта*/
    Site getConfig();

}
