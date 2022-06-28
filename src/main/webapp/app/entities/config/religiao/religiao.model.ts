export interface IReligiao {
  id?: number;
  codigo?: string;
  descricao?: string;
}

export class Religiao implements IReligiao {
  constructor(public id?: number, public codigo?: string, public descricao?: string) {}
}

export function getReligiaoIdentifier(religiao: IReligiao): number | undefined {
  return religiao.id;
}
