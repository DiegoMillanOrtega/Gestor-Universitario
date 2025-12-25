export function formatFormDates(formValue: any): any {
  const data = { ...formValue };

  Object.keys(data).forEach(key => {
    const value = data[key];
    // Si el valor es una instancia de Date, lo formateamos
    if (value instanceof Date) {
      const year = value.getFullYear();
      const month = (value.getMonth() + 1).toString().padStart(2, '0');
      const day = value.getDate().toString().padStart(2, '0');
      
      data[key] = `${year}-${month}-${day}`;
    }
  });

  return data;
}