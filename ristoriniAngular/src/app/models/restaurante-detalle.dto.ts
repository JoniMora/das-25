import { PromocionDTO } from './promocion.dto';
import { SucursalDetalleDTO } from './sucursal-detalle.dto';

export interface RestauranteDetalleDTO {
  id: number;
  nombre: string;
  tipoCocina: string;
  descripcion: string;
  imagenUrl: string | null;
  sucursales: SucursalDetalleDTO[];
  promocionesVigentes: PromocionDTO[];
}