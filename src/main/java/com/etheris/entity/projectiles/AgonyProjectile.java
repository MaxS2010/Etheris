// 1. Додайте ці змінні на початку класу LightBulletEntity:
private boolean isDarkBullet = false;
private int wandPowerLevel = 0;

// 2. Додайте цей метод у клас LightBulletEntity, щоб паличка могла передавати параметри:
public void setDarkEffects(boolean isDark, int powerLevel) {
    this.isDarkBullet = isDark;
    this.wandPowerLevel = powerLevel;
}

// 3. Знайдіть метод onEntityHit (або аналогічний, де наноситься damage цілі) і допишіть туди:
@Override
protected void onEntityHit(net.minecraft.util.hit.EntityHitResult entityHitResult) {
    super.onEntityHit(entityHitResult); // залишаємо базову логіку нанесення шкоди
    
    net.minecraft.entity.Entity target = entityHitResult.getEntity();
    
    // Перевіряємо, чи влучили ми в живу сутність (моба або гравця) на стороні сервера
    if (this.isDarkBullet && !this.getWorld().isClient() && target instanceof net.minecraft.entity.LivingEntity livingTarget) {
        
        // Розрахунок тривалості ефекту (наприклад, 4 секунди базова + 2 секунди за кожен рівень сили палички)
        int duration = 80 + (wandPowerLevel * 40); 
        int amplifier = Math.max(0, wandPowerLevel - 1); // Рівень ефекту (0 = I рівень, 1 = II рівень)

        // 🟢 Накладаємо Сліпоту (BLINDNESS)
        livingTarget.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(
                net.minecraft.entity.effect.StatusEffects.BLINDNESS, duration, 0, false, true));

        // 🟢 Накладаємо Иссушение (WITHER)
        livingTarget.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(
                net.minecraft.entity.effect.StatusEffects.WITHER, duration, amplifier, false, true));

        // 🟢 Накладаємо Отруєння (POISON)
        livingTarget.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(
                net.minecraft.entity.effect.StatusEffects.POISON, duration, amplifier, false, true));
    }
}
